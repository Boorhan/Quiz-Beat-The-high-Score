package com.example.quiz_quiz.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quiz_quiz.api.APIinterface
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.QuestionsAndAnswers
import com.example.quiz_quiz.room.QuestionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuestionsRepository(private val questionApi: APIinterface,
                          private val questionDatabase: QuestionDatabase) {

    private val questionsLiveData = MutableLiveData<QuestionsAndAnswers>()
    //val questionsLiveData: LiveData<List<Question>> = _questionsLiveData
    val question:LiveData<QuestionsAndAnswers>
    get() = questionsLiveData

    suspend fun fetchQuestions() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = questionApi.getQuestions()
                if (response.isSuccessful && response.body() != null) {
                    val questions = response.body()!!.questions

                   // Log.d("Borhan", "fetchQuestions: ${questions?.javaClass}")
                    // Log the number of questions received from the API
                    Log.d("QuestionsRepository", "Received ${questions.size} questions from API")


                    // Insert questions into the database
                    questionDatabase.questionDao().insertQuestions(questions)



                    // Create a list to hold individual answers
                    val answersList = mutableListOf<Answers>()

                    // Iterate through questions and add answers to the list
                    for (question in questions) {
                        answersList.add(question.answers)
                    }

                    // Insert answers into the database
                    questionDatabase.questionDao().insertAnswers(answersList)
                    // Log a message after successful insertion
                    Log.d("QuestionsRepository", "Inserted questions into the database")

                    questionsLiveData.postValue(response.body())
                }
            } catch (e: Exception) {
                // Log the exception for error handling
                Log.e("QuestionsRepository", "Error fetching or inserting questions: ${e.message}", e)

            }
        }
    }
}
