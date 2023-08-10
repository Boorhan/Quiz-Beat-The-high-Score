package com.example.quiz_quiz

import android.app.Application
import com.example.quiz_quiz.api.APIinterface
import com.example.quiz_quiz.api.APiUtilities
import com.example.quiz_quiz.repository.QuestionsRepository
import com.example.quiz_quiz.room.QuestionDatabase

class MyApplication:Application() {

    lateinit var questionsRepository: QuestionsRepository

    override fun onCreate() {
        super.onCreate()

        val questionApi= APiUtilities.createRetrofitInstance().create(APIinterface::class.java);

        val database = QuestionDatabase.getDatabase(applicationContext)
        questionsRepository= QuestionsRepository(questionApi, database, applicationContext)
    }
}