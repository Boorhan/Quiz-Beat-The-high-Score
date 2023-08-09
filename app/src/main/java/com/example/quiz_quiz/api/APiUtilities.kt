package com.example.quiz_quiz.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APiUtilities {

    val BASE_URL= "https://herosapp.nyc3.digitaloceanspaces.com/"

    public fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}