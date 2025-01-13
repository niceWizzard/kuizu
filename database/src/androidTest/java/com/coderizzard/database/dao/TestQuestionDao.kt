package com.coderizzard.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coderizzard.core.data.placeholder.value.quiz
import com.coderizzard.database.data.database.AppDatabase
import com.coderizzard.database.data.database.model.toEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class TestQuestionDao {
    private lateinit var db: AppDatabase
    @Before
    fun createDb() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()  // Only use this in testing
            .build()

        db.quizDao.createQuiz(
            quiz.toEntity()
        )
    }

    @Test
    fun testGetFunctionsReturnEmptyOnEmptyDb() = runBlocking {
        val dao = db.questionDao
        assertTrue(dao.getQuizMCQuestions(quiz.id).isEmpty())
        assertTrue(dao.getQuizIdentificationQuestions(quiz.id).isEmpty())
        assertTrue(dao.getQuizUnsupportedQuestions(quiz.id).isEmpty())



    }


}