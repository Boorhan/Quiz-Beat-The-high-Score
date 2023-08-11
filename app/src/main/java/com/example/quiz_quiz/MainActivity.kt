package com.example.quiz_quiz

import android.content.Intent
import java.lang.Class
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.quiz_quiz.api.APIinterface
import com.example.quiz_quiz.api.APiUtilities
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.QuestionsAndAnswers
import com.example.quiz_quiz.repository.QuestionsRepository
import com.example.quiz_quiz.room.QuestionDatabase
import com.example.quiz_quiz.viewmodel.QuestionViewModelFactory
import com.example.quiz_quiz.viewmodel.QuestionsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var highScoreTextView: TextView
    private lateinit var questionViewModel: QuestionsViewModel
    lateinit var questionsRepository: QuestionsRepository

    private var highScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Borhan", "MainActivity")
        var repository= (application as MyApplication).questionsRepository

        questionViewModel= ViewModelProvider(this, QuestionViewModelFactory(repository)).get(QuestionsViewModel::class.java)

        questionViewModel.question.observe(this, {
            Log.d("Borhan", "onCreate: ${it.toString()}")
            //Toast.makeText(this, "Size: ${it.questions.size}", Toast.LENGTH_LONG).show()

            it.questions.iterator().forEach {q->
                Log.d("Borhan", "Question: ${q.answers}\n Score: ${q.score}")
            }

        })
//        val questionApi=APiUtilities.createRetrofitInstance().create(APIinterface::class.java);
//
//        GlobalScope.launch {
//            val result = questionApi.getQuestions();
//            if(result.body()!=null){
//                //Log.d("Borhan", "onCreate: ${result.body()}")
//
//                //val responseBody = result.body()
////                println("Type of responseBody: ${responseBody?.javaClass}")
////                Log.d("Borhan", "onCreate:  ${responseBody?.javaClass}")
//
//                val responseBody = result.body()
//
//                if (responseBody is QuestionsAndAnswers) {
//                    val questionsList = responseBody.questions
//                    for (question in questionsList) {
//                        // Process each question here
//                        Log.d("Borhan", "onCreate:  ${question.answers}")
//                    }
//                } else {
//                    // Handle other cases
//                }
//
//            }else{
//                Log.d("Borhan", "onCreate: Nothing is fetched")
//            }
//        }
        

        highScoreTextView = findViewById(R.id.highScoreTextView)
        updateHighScore()
    }

    fun startNewGame(view: View) {

        // Code to start a new game goes here
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
    }

    private fun updateHighScore() {
        highScoreTextView.text = "High Score: $highScore"
    }
}