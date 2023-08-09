package com.example.quiz_quiz

import android.content.Intent
import java.lang.Class
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.quiz_quiz.api.APIinterface
import com.example.quiz_quiz.api.APiUtilities
import com.example.quiz_quiz.model.Answers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var highScoreTextView: TextView

    private var highScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionApi=APiUtilities.createRetrofitInstance().create(APIinterface::class.java);

        GlobalScope.launch {
            val result = questionApi.getQuestions();
            if(result.body()!=null){
                Log.d("Borhan", "onCreate: ${result.body()}")


//                result.body()!!.forEach { question ->
//                    // Do something with each question
//                }

            }else{
                Log.d("Borhan", "onCreate: Nothing is fetched")
            }
        }
        

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