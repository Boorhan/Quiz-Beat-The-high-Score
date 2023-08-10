package com.example.quiz_quiz.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.Question

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<Answers>)

    @Query("SELECT * FROM question WHERE question = :questionText")
    suspend fun getQuestionByQuestionText(questionText: String): Question?

    @Query("SELECT * FROM answers WHERE A = :a AND B = :b AND C = :c AND D = :d")
    suspend fun getAnswerByValues(a: String, b: String, c: String, d: String): Answers?

    @Query("SELECT * FROM question")
    suspend fun getQuestions(): List<Question>

    @Query("SELECT * FROM answers")
    suspend fun getAnswers(): List<Answers>
}