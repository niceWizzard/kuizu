package com.coderizzard.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coderizzard.core.data.placeholder.value.quiz
import com.coderizzard.core.data.placeholder.value.quizSession
import com.coderizzard.database.data.database.AppDatabase
import com.coderizzard.database.data.database.dao.QuizDao
import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.toEntity
import com.coderizzard.database.data.database.model.toEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class TestSessionDao {

    private lateinit var db: AppDatabase
    private lateinit var dao : SessionDao
    @Before
    fun createDb() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.sessionDao


        db.quizDao.createQuiz(
            quiz.toEntity()
        )
    }

    @Test
    fun testCreateSessionEqualsInitialized() = runBlocking {

        val sessionEntity = quizSession.toEntity()
        dao.createSession(sessionEntity)

        Assert.assertTrue(dao.hasActiveSession(quiz.id))

        val returned = dao.getActiveSession(quiz.id)
        Assert.assertEquals(sessionEntity, returned)

    }


    @Test
    fun testDeleteSession() = runBlocking {
        val sessionEntity = quizSession.toEntity()
        dao.createSession(sessionEntity)

        Assert.assertTrue(dao.hasActiveSession(quiz.id))
        dao.deleteActiveSession(quiz.id)
        Assert.assertFalse(dao.hasActiveSession(quiz.id))
    }

    @Test
    fun testGetAll() = runBlocking {
        val quizList = List(5) {
            quiz.copy(
                id = UUID.randomUUID().toString(),
            )
        }
        quizList.forEach {
            db.quizDao.createQuiz(it.toEntity())
            dao.createSession(
                QuizSessionEntity(
                    quizId = it.id,
                    currentQuestionIndex = 0,
                    questionOrder = it.supportedQuestions.map { it.id },
                    startedAt = LocalDateTime.now(),
                )
            )
        }
        val sessionsList = dao.getAll().first()
        Assert.assertEquals(sessionsList.size, quizList.size)
        sessionsList.forEach { ses ->
            Assert.assertNotNull(quizList.find { ses.quizId == it.id })
        }




    }


}