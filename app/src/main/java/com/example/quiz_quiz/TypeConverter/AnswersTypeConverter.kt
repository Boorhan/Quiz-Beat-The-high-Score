package com.example.quiz_quiz.TypeConverter

import androidx.room.TypeConverter
import com.example.quiz_quiz.model.Answers
import com.google.gson.Gson

class AnswersTypeConverter {

    @TypeConverter
    fun fromAnswers(answers: Answers): String {
        val gson = Gson()
        return gson.toJson(answers)
    }

    @TypeConverter
    fun toAnswers(answersJson: String): Answers {
        val gson = Gson()
        return gson.fromJson(answersJson, Answers::class.java)
    }
}
