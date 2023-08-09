package com.example.quiz_quiz.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.Question

@Dao
interface RoomDao {

    @Insert
    suspend fun insertQuestions(questions: List<Question>)

    @Insert
    suspend fun insertAnswers(answers: List<Answers>)

    @Query("SELECT * FROM question")
    suspend fun getQuestions(): List<Question>

    @Query("SELECT * FROM answers")
    suspend fun getAnswers(): List<Answers>
}