package com.example.quiz_quiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz_quiz.model.QuestionsAndAnswers
import com.example.quiz_quiz.repository.QuestionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class QuestionsViewModel(private val repository: QuestionsRepository) : ViewModel() {

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchQuestions()
        }

    }

    val question:LiveData<QuestionsAndAnswers>
        get() = repository.question
}