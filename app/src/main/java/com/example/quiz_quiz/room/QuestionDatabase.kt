package com.example.quiz_quiz.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quiz_quiz.TypeConverter.AnswersTypeConverter
import com.example.quiz_quiz.model.Answers
import com.example.quiz_quiz.model.HighScore
import com.example.quiz_quiz.model.Question

@Database(entities = [Question::class, Answers::class, HighScore::class], version = 5)
@TypeConverters(AnswersTypeConverter::class)
abstract class QuestionDatabase : RoomDatabase(){

    abstract fun questionDao() : RoomDao

    companion object{
        private var INSTANCE: QuestionDatabase?=null

        fun getDatabase(context: Context):QuestionDatabase{
            if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(
                        context,
                        QuestionDatabase::class.java,
                        "questionDB"
                    ).fallbackToDestructiveMigration().build()

            }

            return INSTANCE!!
        }
    }
}