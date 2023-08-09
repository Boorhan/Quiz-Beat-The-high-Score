package com.example.quiz_quiz.api

import com.example.quiz_quiz.model.QuestionsAndAnswers
import retrofit2.Response
import retrofit2.http.GET

interface APIinterface {

    @GET("/quiz.json")
    suspend fun getQuestions() : Response<QuestionsAndAnswers>
}