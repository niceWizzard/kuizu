package com.coderizzard.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coderizzard.core.data.placeholder.value.quiz
import com.coderizzard.database.data.database.AppDatabase
import com.coderizzard.database.data.database.dao.QuizDao
import com.coderizzard.database.data.database.dao.SessionResultDao
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.session.SessionResultEntity
import com.coderizzard.database.data.database.model.toEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class TestSessionResultDao {
    private lateinit var quizDao: QuizDao
    private lateinit var dao: SessionResultDao
    private lateinit var db: AppDatabase

    private val testId = UUID.randomUUID().toString()
    private val testSessionResult = SessionResultEntity(
        id = testId,
        quizId = quiz.id,
        marks = 10,
        totalPoints = 10,
        dateFinished = LocalDateTime.now(),
    )
    private val testQuizEntity = quiz.toEntity()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.sessionResultDao
        quizDao = db.quizDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCreateAndGetResult() = runBlocking {
        quizDao.createQuiz(testQuizEntity)
        dao.createResult(testSessionResult)
        val returnedSesResult = dao.getLatestResult(quiz.id)
        Assert.assertEquals(
            "Expected the value to be equal to test value but got <${returnedSesResult}>",
            testSessionResult,
            returnedSesResult,
        )
    }

    @Test
    fun testGetLatestResultWithEmptyDb() = runBlocking {
        val result = dao.getLatestResult(quiz.id)
        Assert.assertNull(
            "Expected the value to be null but got <${result}>",
            result
        )
    }

    @Test
    fun testGetAllReturnsValidValues() = runBlocking {
        quizDao.createQuiz(testQuizEntity)

        val testSessions = List(10) {
            SessionResultEntity(
                id = "test-session-${it + 1}",
                dateFinished = LocalDateTime.now(),
                quizId = quiz.id,
                marks = Random.nextInt(10) ,
                totalPoints = 10,
            )
        }
        testSessions.forEach {ses ->
            dao.createResult(ses)
        }
        val resultList = dao.getAllResult(quiz.id)
        Assert.assertEquals(
            "Expected SessionResult list to be equal but got <$resultList>",
            testSessions.toSet(),
            resultList.toSet(),
        )
    }
}