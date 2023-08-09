package com.example.quiz_quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class Answers(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Auto-generated primary key
    val A: String?,
    val B: String?,
    val C: String?,
    val D: String?
)