package com.example.quiz_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.quiz_quiz.viewmodel.QuestionViewModelFactory
import com.example.quiz_quiz.viewmodel.QuestionsViewModel
import android.widget.TextView
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.Question


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
    private var buttonA: Button? = null
    private var buttonB: Button? = null
    private var buttonC: Button? = null
    private var buttonD: Button? = null

    private var countDownTimer: CountDownTimer? = null
    private val COUNTDOWN_INTERVAL = 1000L // 1 second



    private var currentQuestionIndex = 0
    private var scoreCount = 0
    private var correctScoreCount = 0
    private var wrongScoreCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        initializeViews()
        initializeDatabse()
        buttonClickListener()

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
            tvCurQuesNo!!.text = currentQuestionIndex.toString()
            scoreCountText!!.text =scoreCount.toString()
            correctTextView!!.text = correctScoreCount.toString()
            wrongTextView!!.text= wrongScoreCount.toString()
            //from customQuestionsList
            scoreTextView!!.text=item.score.toString()
            questionTextView!!.text = item.question.toString()
            totalQuestionTextView!!.text = customQuestionsList.size.toString()
            buttonA!!.text=item.answers.A
            buttonB!!.text=item.answers.B
            buttonC!!.text=item.answers.C
            buttonD!!.text=item.answers.D

        }
        else {
            // Handle case when all questions have been displayed
            questionTextView!!.text = "No more questions"
            countDownTimer?.cancel()
        }

    }

    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(10000L, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                // Update countdown UI if needed
                val secondsRemaining = (millisUntilFinished + 1000) / 1000 // Add 1000 milliseconds and then divide

                timerTextView!!.text=secondsRemaining.toString()

            }

            override fun onFinish() {
                // Move to the next question when timer finishes
                currentQuestionIndex++
                updateUI()
            }
        }

        countDownTimer?.start()
    }

    private fun buttonClickListener(){
        val buttonClickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.buttonA -> {
                    // Handle button A click
                }
                R.id.buttonB -> {
                    // Handle button B click
                }
                R.id.buttonC -> {
                    // Handle button A click
                }
                R.id.buttonD -> {
                    // Handle button B click
                }

            }
        }

        buttonA!!.setOnClickListener(buttonClickListener)
        buttonB!!.setOnClickListener(buttonClickListener)
        buttonC!!.setOnClickListener(buttonClickListener)
        buttonD!!.setOnClickListener(buttonClickListener)
    }

}