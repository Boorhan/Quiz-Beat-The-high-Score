package com.example.quiz_quiz.repository
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quiz_quiz.api.APIinterface
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.QuestionsAndAnswers
import com.example.quiz_quiz.room.QuestionDatabase
import com.example.quiz_quiz.room.RoomDao
import com.example.quiz_quiz.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuestionsRepository(
    private val questionApi: APIinterface,
    private val questionDatabase: QuestionDatabase,
    private val applicationContext: Context
) {

    private val questionsLiveData = MutableLiveData<QuestionsAndAnswers>()
    //val questionsLiveData: LiveData<List<Question>> = _questionsLiveData
    val question:LiveData<QuestionsAndAnswers>
    get() = questionsLiveData


    suspend fun fetchQuestions() {

        if(NetworkUtil.isInternetAvailable(applicationContext)){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = questionApi.getQuestions()
                    if (response.isSuccessful && response.body() != null) {
                        val questions = response.body()!!.questions
                        Log.d("QuestionsRepository", "Received ${questions.size} questions from API")



                        questionDatabase.questionDao().insertQuestions(questions)



                        // Create a list to hold individual answers
                        val answersList = mutableListOf<Answers>()

                        // Iterate through questions and add answers to the list
                        for (question in questions) {
                            answersList.add(question.answers)
                        }

                        questionDatabase.questionDao().insertAnswers(answersList)

                        Log.d("QuestionsRepository", "Inserted questions into the database")

                        questionsLiveData.postValue(response.body())
                    }
                } catch (e: Exception) {
                    // Log the exception for error handling
                    Log.e("QuestionsRepository", "Error fetching or inserting questions: ${e.message}", e)

                }
            }
        }else{
            val questions = questionDatabase.questionDao().getQuestions()
            val answers = questionDatabase.questionDao().getAnswers()

            val quesList= QuestionsAndAnswers(questions)
            questionsLiveData.postValue(quesList)

        }

    }
}
