package com.example.quiz_quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz_quiz.repository.QuestionsRepository

class QuestionViewModelFactory (val repository: QuestionsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionsViewModel(repository) as T
    }
}