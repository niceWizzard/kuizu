package com.coderizzard.database.dao

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.database.data.database.AppDatabase
import com.coderizzard.database.data.database.dao.QuizDao
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.UUID

@RunWith(AndroidJUnit4::class)
internal class TestQuizDao {
    private lateinit var db: AppDatabase
    private lateinit var dao : QuizDao

    private val remoteId = UUID.randomUUID().toString()
    private val quizName = "Some name"
    private val globalQuiz = QuizEntity(
        remoteId = remoteId,
        id = "id",
        name = quizName,
        imageLink = "",
        author = "Author",
        createdAt = LocalDateTime.now(),
        localImagePath = ""
    )
    private val globalQuestionList = listOf(
        IdentificationQuestionEntity(
            text = "What is 1+1?",
            quizId = globalQuiz.id,
            id = UUID.randomUUID().toString(),
            remoteId = UUID.randomUUID().toString(),
            imageLink = "",
            answer = "2",
            point = 1,
            localImagePath = "",
        )
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()  // Only use this in testing
            .build()

        dao = db.quizDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testIsRemoteIdUsed() = runBlocking {
        for(i in 1 .. 2) {
            val id = UUID.randomUUID().toString()
            dao.createQuiz(globalQuiz.copy(id = UUID.randomUUID().toString(),remoteId = id))
            Assert.assertTrue(
                dao.isRemoteIdUsed(id)
            )
        }
    }

    @Test
    fun testGetAll() = runBlocking {
        dao.createQuiz(globalQuiz)
        var quizList = dao.getAll().first()
        Assert.assertNotNull(
            "getAll should returned the quiz",
            quizList.find { it.id == globalQuiz.id },
        )
        Assert.assertEquals(
            "The quiz list should only be containing 1 item but got <${quizList.size}>",
            1,
            quizList.size,
        )

        val newId = UUID.randomUUID().toString()
        dao.createQuiz(globalQuiz.copy(id = newId))
        quizList = dao.getAll().first()
        Assert.assertNotNull(
            "getAll should returned the quiz",
            quizList.find { it.id == newId },
        )
        Assert.assertEquals(
            "The quiz list should now be containing 2 items but got <${quizList.size}>",
            2,
            quizList.size,
        )
    }

    @Test
    fun testGetById() = runBlocking  {
        dao.createQuiz(globalQuiz)
        val q = dao.getById(globalQuiz.id)
        Assert.assertEquals(
            "The global quiz and returned quiz should be equal",
            globalQuiz,
            q,
        )

    }

    @Test
    fun testGetByIdWithNonexistentId() = runBlocking {
        dao.createQuiz(globalQuiz)
        Assert.assertNull("The function getById should return null when given a non-existing id",
            dao.getById("some random id")
        )
    }

    @Test
    fun testDeleteQuiz() = runBlocking {
        dao.createQuiz(globalQuiz)
        dao.deleteQuiz(globalQuiz.id)
        Assert.assertNull("There should be no quiz returned when the quiz was deleted",dao.getById(globalQuiz.id))
        val list = dao.getAll().first()
        Assert.assertEquals(
            "getAll should return empty list but got <${list}>",
            0,
            list.size,
        )
        try {
            dao.createQuiz(globalQuiz)
            Assert.assertTrue(true)
        } catch (e : Exception) {
            Assert.assertTrue("There should be no error thrown",false)
        }
    }

    @Test
    fun testCreateQuizWithOptions() = runBlocking {
        dao.createQuiz(
            globalQuiz,
            questions = globalQuestionList,
            options = emptyList(),
            questionDao = db.questionDao,
        )

        // Assert Quiz
        val quiz = dao.getById(globalQuiz.id)
        Assert.assertEquals(
            "Quiz should match the globally defined quiz entity",
            globalQuiz,
            quiz
        )

        // Assert Questions
        val questions = db.questionDao.getQuizIdentificationQuestions(globalQuiz.id)
        Assert.assertEquals(
            "Saved questions should match the predefined global question list",
            globalQuestionList.toSet(),
            questions.toSet()
        )
    }

}