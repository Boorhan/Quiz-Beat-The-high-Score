package com.example.quiz_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class QuizActivity : AppCompatActivity() {
    private lateinit var questionCountTextView: TextView
    private lateinit var currentScoreTextView: TextView
    private lateinit var questionCardView: CardView
    private lateinit var questionTextView: TextView
    private lateinit var answersLinearLayout: LinearLayout

    private val questions = mutableListOf("Question 1", "Question 2", "Question 3")
    private val correctAnswers = arrayOf(2, 1, 3)
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionCountTextView = findViewById(R.id.questionCountTextView)
        currentScoreTextView = findViewById(R.id.currentScoreTextView)
        questionCardView = findViewById(R.id.questionCardView)
        questionTextView = findViewById(R.id.questionTextView)
        answersLinearLayout = findViewById(R.id.answersLinearLayout)

        updateQuestionCount()
        updateCurrentScore()
        //displayQuestion()
    }

    private fun updateQuestionCount() {
        val questionCount = questions.size
        questionCountTextView.text = "Question ${currentQuestionIndex + 1} of $questionCount"
    }

    private fun updateCurrentScore() {
        currentScoreTextView.text = "Score: $score"
    }

}