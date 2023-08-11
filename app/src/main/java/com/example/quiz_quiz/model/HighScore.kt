package com.example.quiz_quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_score_table")
data class HighScore(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val score: Long?
)