package com.example.quiz_quiz

import android.content.Intent
import java.lang.Class
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
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

    private var highScore: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var repository= (application as MyApplication).questionsRepository

        questionViewModel= ViewModelProvider(this, QuestionViewModelFactory(repository)).get(QuestionsViewModel::class.java)

        questionViewModel.question.observe(this, {
            Log.d("Borhan", "onCreate: ${it.toString()}")
            //Toast.makeText(this, "Size: ${it.questions.size}", Toast.LENGTH_LONG).show()

            it.questions.iterator().forEach {q->
                Log.d("Borhan", "Question: ${q.answers}\n Score: ${q.score}")
            }

        })

        highScoreTextView = findViewById(R.id.highScoreTextView)
        updateHighScore()
        getHighScoreFromDatabase()
    }

    private fun getHighScoreFromDatabase(){
        questionViewModel.getHighestScore().observe(this, Observer { highestScore ->
            // Update UI with highestScore
            if (highestScore != null) {
                highScore= highestScore.score!!
                highScoreTextView!!.text=highScore.toString()
            }
        })
    }

    fun startNewGame(view: View) {

        // Code to start a new game goes here
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateHighScore() {
        highScoreTextView.text = "High Score: $highScore"
    }
}