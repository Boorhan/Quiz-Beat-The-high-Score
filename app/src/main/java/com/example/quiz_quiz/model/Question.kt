package com.example.quiz_quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Auto-generated primary key
    val answers: Answers,
    val correctAnswer: String,
    val question: String,
    val questionImageUrl: String,
    val score: Int
)