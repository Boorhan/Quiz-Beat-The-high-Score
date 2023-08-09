package com.example.quiz_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var highScoreTextView: TextView

    private var highScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        highScoreTextView = findViewById(R.id.highScoreTextView)
        updateHighScore()
    }

    fun startNewGame(view: View) {

        // Code to start a new game goes here
    }

    private fun updateHighScore() {
        highScoreTextView.text = "High Score: $highScore"
    }
}