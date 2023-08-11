package com.example.quiz_quiz

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.quiz_quiz.viewmodel.QuestionViewModelFactory
import com.example.quiz_quiz.viewmodel.QuestionsViewModel
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.HighScore
import com.example.quiz_quiz.model.Question
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class QuizActivity : AppCompatActivity() {

    private lateinit var questionViewModel: QuestionsViewModel

    private lateinit var customQuestionsList: List<Question>

    // Declare variables for all the views you want to identify
    private var coinImageView: TextView? = null
    private var tvCurQuesNo: TextView? = null
    private var scoreCountText: TextView? = null
    private var totalQuestionTextView: TextView? = null
    private var appTitleTextView: TextView? = null
    private var correctTextView: TextView? = null
    private var wrongTextView: TextView? = null
    private var correctArrowImageView: ImageView? = null
    private var wrongArrowImageView: ImageView? = null
    private var timerImageView: ImageView? = null
    private var timerTextView: TextView? = null
    private var scoreTextView: TextView? = null
    private var questionImageView: ImageView? = null
    private var questionTextView: TextView? = null
    private var buttonA: MaterialButton? = null
    private var buttonB: MaterialButton? = null
    private var buttonC: MaterialButton? = null
    private var buttonD: MaterialButton? = null

    private var countDownTimer: CountDownTimer? = null
    private val COUNTDOWN_INTERVAL = 1000L // 1 second
    private lateinit var shakeAnimation: Animation

    //Variables
    private var currentQuestionIndex = 0
    private var scoreCount = 0
    private var totalScoreCount = 0
    private var correctScoreCount = 0
    private var wrongScoreCount = 0
    private var curCorrectAns = ""
    private var imgURL = ""
    private var highScoreCount = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        initializeViews()
        initializeDatabse()
        buttonClickListener()
        getHighScoreFromDatabase()
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
        countDownTimer = object : CountDownTimer(10000L, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                // Update countdown UI if needed
                val secondsRemaining = (millisUntilFinished + 1000) / 1000 // Add 1000 milliseconds and then divide

                timerTextView!!.text=secondsRemaining.toString()

            }

            override fun onFinish() {
                // Move to the next question when timer finishes
                currentQuestionIndex++
                timerTextView!!.text="0"
                updateUI()
            }
        }

    }

    private fun initializeViews() {
        // Initialize the views using findViewById
        tvCurQuesNo = findViewById(R.id.txtQnum)
        scoreCountText = findViewById(R.id.scoreCountText)
        totalQuestionTextView = findViewById(R.id.totalQuestion)
        appTitleTextView = findViewById(R.id.textView)
        correctTextView = findViewById(R.id.txtCorrect)
        wrongTextView = findViewById(R.id.txtWrong)
        timerImageView = findViewById(R.id.imageView2)
        timerTextView = findViewById(R.id.txtTimer)
        scoreTextView = findViewById(R.id.scoreTextView)
        questionImageView = findViewById(R.id.imageView)
        questionTextView = findViewById(R.id.questionTextView)
        buttonA = findViewById(R.id.buttonA)
        buttonB = findViewById(R.id.buttonB)
        buttonC = findViewById(R.id.buttonC)
        buttonD = findViewById(R.id.buttonD)

    }

    private fun getHighScoreFromDatabase(){
        questionViewModel.getHighestScore().observe(this, Observer { highestScore ->
            // Update UI with highestScore
            if (highestScore != null) {
                highScoreCount= highestScore.score!!
                //appTitleTextView!!.text=highScoreCount.toString()
            }
        })
    }

    private fun initializeDatabse(){
        var repository= (application as MyApplication).questionsRepository

        questionViewModel= ViewModelProvider(this, QuestionViewModelFactory(repository)).get(
            QuestionsViewModel::class.java)

        questionViewModel.question.observe(this, {
            //Log.d("Borhan", "onCreate: ${it.toString()}")
            //Toast.makeText(this, "Size: ${it.questions.size}", Toast.LENGTH_LONG).show()


            val quesList =it.questions
            Log.d("ScoobyDooby", "onCreate: ${it.questions}")
            questionViewModel.populateQuestionList(quesList)


            val answersList = mutableListOf<Answers>()

            // Iterate through questions and add answers to the list
            for (question in it.questions) {
                answersList.add(question.answers)
            }
            Log.d("ScoobyDooby", "Ans: ${answersList}")
            questionViewModel.populateAnswerList(answersList)


            //updateUI()
            customQuestionsList = questionViewModel.getCustomQuestionList()
            updateUI()

        })

    }

    private fun updateUI(){
//        val customQuestionsList = questionViewModel.getCustomQuestionList()
//        var customAnsList = questionViewModel.getCustomAnswerList()

        startCountdownTimer()
        // You can now use customQuestionsList and customAnsList to update your UI
        if (customQuestionsList.isNotEmpty() && currentQuestionIndex < customQuestionsList.size) {
            val item=customQuestionsList[currentQuestionIndex]
            //from variables
            tvCurQuesNo!!.text = (currentQuestionIndex+1).toString()
            scoreCountText!!.text =totalScoreCount.toString()
            if(totalScoreCount>=highScoreCount){
                CoroutineScope(Dispatchers.Main).launch {
                    highScoreCount= totalScoreCount.toLong()
                    val highScore = HighScore(score = highScoreCount)
                    questionViewModel.insertHighScore(highScore)// This function should also be suspend
                }
            }

            correctTextView!!.text = correctScoreCount.toString()
            wrongTextView!!.text= wrongScoreCount.toString()
            //from customQuestionsList
            scoreCount=item.score
            scoreTextView!!.text=scoreCount.toString()+" Points"
            questionTextView!!.text = item.question.toString()
            totalQuestionTextView!!.text = customQuestionsList.size.toString()

            resetButtonColor()
            resetButtonVisibility()
            clearButtonAnimation()
            if(item.answers.A==null){
                buttonA!!.visibility= View.GONE
            }
            if(item.answers.B==null){
                buttonB!!.visibility= View.GONE
            }

            if(item.answers.C==null){
                buttonC!!.visibility= View.GONE
            }

            if(item.answers.D==null){
                buttonD!!.visibility= View.GONE
            }
            buttonA!!.text=item.answers.A
            buttonB!!.text=item.answers.B
            buttonC!!.text=item.answers.C
            buttonD!!.text=item.answers.D

            curCorrectAns=item.correctAnswer.toString()
            imgURL=item.questionImageUrl.toString()
            if(imgURL!="null"){
                //Log.d("ScoobyDooby", "borhan is here")
                Picasso.get().load(imgURL).into(questionImageView);
            }else{
                //Log.d("Quiz activity", "ImgURL is null ")
                questionImageView!!.setImageResource(R.drawable.quiz_logo)
            }

        }
        else {
            // Handle case when all questions have been displayed
            questionTextView!!.text = "No more questions"
            countDownTimer?.cancel()
        }

    }

    private fun clearButtonAnimation(){
        buttonA!!.clearAnimation()
        buttonB!!.clearAnimation()
        buttonC!!.clearAnimation()
        buttonD!!.clearAnimation()
    }
    private fun startCountdownTimer() {
        countDownTimer?.start()
    }

    private fun delay2sec(){

        countDownTimer?.cancel()

        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView!!.text= (millisUntilFinished/ 1000).toString()
            }
            override fun onFinish() {
                countDownTimer?.onFinish()
            }
        }.start()
    }

    private fun resetButtonVisibility(){
        buttonA!!.visibility=View.VISIBLE
        buttonB!!.visibility=View.VISIBLE
        buttonC!!.visibility=View.VISIBLE
        buttonD!!.visibility=View.VISIBLE
    }
    private fun resetButtonColor(){
        buttonA!!.setStrokeColorResource(R.color.white)
        buttonA!!.isEnabled=true
        buttonB!!.setStrokeColorResource(R.color.white)
        buttonB!!.isEnabled=true
        buttonC!!.setStrokeColorResource(R.color.white)
        buttonC!!.isEnabled=true
        buttonD!!.setStrokeColorResource(R.color.white)
        buttonD!!.isEnabled=true
    }

    private fun buttonClickedFalse(){
        buttonA!!.isEnabled=false
        buttonB!!.isEnabled=false
        buttonC!!.isEnabled=false
        buttonD!!.isEnabled=false
    }

    private fun changeStrokeColor(ans:String, buttonClicked: String){

        if(ans=="A"){
            buttonA!!.setStrokeColorResource(R.color.lightGreen)
        }else if(ans=="B"){
            buttonB!!.setStrokeColorResource(R.color.lightGreen)
        }else if(ans=="C"){
            buttonC!!.setStrokeColorResource(R.color.lightGreen)
        }else if(ans=="D"){
            buttonD!!.setStrokeColorResource(R.color.lightGreen)
        }

        if(buttonClicked=="A" && ans!="A"){
            buttonA!!.setStrokeColorResource(R.color.lightRed)
        }else if(buttonClicked=="B" && ans!="B"){
            buttonB!!.setStrokeColorResource(R.color.lightRed)
        }else if(buttonClicked=="C" && ans!="C"){
            buttonC!!.setStrokeColorResource(R.color.lightRed)
        }else if(buttonClicked=="D" && ans!="C"){
            buttonD!!.setStrokeColorResource(R.color.lightRed)
        }
    }

    private fun buttonClickListener(){
        val buttonClickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.buttonA -> {
                    buttonA!!.setStrokeColorResource(R.color.lightGreen)

                   // Toast.makeText(this, "CorrectAns: ${curCorrectAns}", Toast.LENGTH_SHORT).show()

                    if(curCorrectAns=="A"){

                        totalScoreCount+=scoreCount
                        scoreCountText!!.text=totalScoreCount.toString()
                        correctScoreCount+=1
                        correctTextView!!.text=correctScoreCount.toString()
                    }else{
                        wrongScoreCount+=1
                        wrongTextView!!.text=wrongScoreCount.toString()
                        Log.d("ScoobyDooby", "No: Wrong")
                    }


                    buttonA!!.startAnimation(shakeAnimation)
                    changeStrokeColor(curCorrectAns,"A")
                    buttonClickedFalse()
                    delay2sec()
                }
                R.id.buttonB -> {

                    if(curCorrectAns=="B"){
                        totalScoreCount+=scoreCount
                        scoreCountText!!.text=totalScoreCount.toString()
                        correctScoreCount+=1
                        correctTextView!!.text=correctScoreCount.toString()
                    }else{
                        wrongScoreCount+=1
                        wrongTextView!!.text=wrongScoreCount.toString()
                        Log.d("ScoobyDooby", "No: Wrong")
                    }

                    changeStrokeColor(curCorrectAns,"B")
                    buttonB!!.startAnimation(shakeAnimation)
                    buttonClickedFalse()
                    delay2sec()
                }
                R.id.buttonC -> {


                    if(curCorrectAns=="C"){
                        totalScoreCount+=scoreCount
                        scoreCountText!!.text=totalScoreCount.toString()
                        correctScoreCount+=1
                        correctTextView!!.text=correctScoreCount.toString()
                    }else{
                        wrongScoreCount+=1
                        wrongTextView!!.text=wrongScoreCount.toString()
                        Log.d("ScoobyDooby", "No: Wrong")
                    }
                    changeStrokeColor(curCorrectAns,"C")
                    buttonC!!.startAnimation(shakeAnimation)
                    buttonClickedFalse()
                    delay2sec()
                }
                R.id.buttonD -> {

                    if(curCorrectAns=="D"){
                        totalScoreCount+=scoreCount
                        scoreCountText!!.text=totalScoreCount.toString()
                        correctScoreCount+=1
                        correctTextView!!.text=correctScoreCount.toString()
                    }else{
                        wrongScoreCount+=1
                        wrongTextView!!.text=wrongScoreCount.toString()
                        Log.d("ScoobyDooby", "No: Wrong")
                    }
                    changeStrokeColor(curCorrectAns,"D")
                    buttonD!!.startAnimation(shakeAnimation)
                    buttonClickedFalse()
                    delay2sec()
                }

            }
        }

        buttonA!!.setOnClickListener(buttonClickListener)
        buttonB!!.setOnClickListener(buttonClickListener)
        buttonC!!.setOnClickListener(buttonClickListener)
        buttonD!!.setOnClickListener(buttonClickListener)
    }

}