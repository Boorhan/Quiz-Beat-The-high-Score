package com.example.quiz_quiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.Question
import com.example.quiz_quiz.model.QuestionsAndAnswers
import com.example.quiz_quiz.repository.QuestionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class QuestionsViewModel(private val repository: QuestionsRepository) : ViewModel() {
    private var questionList = mutableListOf<Question>() // Your custom list
    private var answerList = mutableListOf<Answers>() // Your custom list

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchQuestions()
        }

    }
    val question:LiveData<QuestionsAndAnswers>
        get() = repository.question

    fun populateQuestionList(questions: List<Question>) {
        questionList.addAll(questions)
    }

    fun populateAnswerList(answers: List<Answers>) {
        answerList.addAll(answers)
    }
    fun getCustomAnswerList(): List<Answers> {
        return answerList
    }

    fun getCustomQuestionList(): List<Question> {
        return questionList
    }



}