package com.coderizzard.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.placeholder.value.identificationQuestion
import com.coderizzard.core.data.placeholder.value.multipleChoiceQuestion
import com.coderizzard.core.data.placeholder.value.quiz
import com.coderizzard.database.data.database.AppDatabase
import com.coderizzard.database.data.database.dao.SessionAnswerDao
import com.coderizzard.database.data.database.model.question.toEntity
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.answers.IdentificationAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.MCQuestionAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer
import com.coderizzard.database.data.database.model.toEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
internal class TestSessionAnswerDao {
    private lateinit var db: AppDatabase
    private lateinit var dao : SessionAnswerDao
    @Before
    fun createDb() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()  // Only use this in testing
            .build()

        db.quizDao.createQuiz(
            quiz.toEntity()
        )
        dao = db.sessionAnswerDao

        db.sessionDao.createSession(
            QuizSessionEntity(
                quizId = quiz.id,
                questionOrder = quiz.supportedQuestions.shuffled().map { it.id },
                startedAt = LocalDateTime.now(),
                currentQuestionIndex = 0,
            )
        )
    }


    @Test
    fun testCreateIQAnswerReturnsIsCorrect() = runBlocking{
        val answer = IdentificationAnswerEntity(
            quizId = quiz.id,
            questionId = identificationQuestion.id,
            correctAnswer = identificationQuestion.answer,
            isCorrect = true,
        )
        dao.createSessionAnswer(
            answer
        )


        Assert.assertEquals(1, dao.getCurrentSessionScore(quiz.id))
        val answers = dao.getAllIdentificationFromSession(quiz.id)
        Assert.assertTrue(answers.any { it == answer })
    }

    @Test
    fun testCreateMCAnswerReturnsIsCorrect() = runBlocking{
        val answer = MCQuestionAnswerEntity(
            quizId = quiz.id,
            questionId = identificationQuestion.id,
            isCorrect = true,
            correctAnswerIds = multipleChoiceQuestion.answer,
        )
        dao.createSessionAnswer(
            answer
        )

        Assert.assertEquals(1, dao.getCurrentSessionScore(quiz.id))
        val answers = dao.getAllMCQuestionAnswerFromSession(quiz.id)
        Assert.assertTrue(answers.any { it == answer })
    }

    @Test
    fun testDeleteMCAnswer() = runBlocking{


        val answer = MCQuestionAnswerEntity(
            quizId = quiz.id,
            questionId = identificationQuestion.id,
            isCorrect = true,
            correctAnswerIds = multipleChoiceQuestion.answer,
        )
        dao.createSessionAnswer(
            answer
        )

        dao.deleteAllMCQuestionAnswer(quiz.id)

        Assert.assertEquals(0,dao.getAllMCQuestionAnswerFromSession(quiz.id).size)
        Assert.assertEquals(0,dao.getCurrentSessionScore(quiz.id))
    }

    @Test
    fun testDeleteIQAnswer()= runBlocking {
        val answer = IdentificationAnswerEntity(
            quizId = quiz.id,
            questionId = identificationQuestion.id,
            correctAnswer = identificationQuestion.answer,
            isCorrect = true,
        )
        dao.createSessionAnswer(
            answer
        )

        dao.deleteAllIdentificationAnswer(quiz.id)

        Assert.assertEquals(0,dao.getAllIdentificationFromSession(quiz.id).size)
        Assert.assertEquals(0,dao.getCurrentSessionScore(quiz.id))
    }

    @Test
    fun testGetCurrentSessionScore() = runBlocking {
        val quizId = "other quiz"
        val newQuiz = quiz.copy(id = quizId, allQuestions = List(10) {
            val isMC = Random.nextBoolean()
            if(isMC) {
                multipleChoiceQuestion.copy(id = UUID.randomUUID().toString(), quizId = quizId)
            } else {
                identificationQuestion.copy(id = UUID.randomUUID().toString(), quizId =  quizId)
            }
        })
        db.quizDao.createQuiz(newQuiz.toEntity())

        db.sessionDao.createSession(
            QuizSessionEntity(
                quizId = quizId,
                currentQuestionIndex = 0,
                questionOrder = newQuiz.supportedQuestions.shuffled().map { it.id },
                startedAt = LocalDateTime.now()
            )
        )

        var countedScore = 0
        newQuiz.supportedQuestions.forEach { q ->
            val isCorrect = Random.nextBoolean()
            countedScore += if (isCorrect) 1 else 0
            dao.createSessionAnswer(
                when(q) {
                    is IdentificationQuestion -> {
                        IdentificationAnswerEntity(
                            quizId = quizId,
                            correctAnswer = "rnalkjsfad",
                            isCorrect = isCorrect,
                            questionId = q.id,
                        )
                    }
                    is MCQuestion -> {
                        MCQuestionAnswerEntity(
                            quizId = quizId,
                            isCorrect = isCorrect,
                            questionId = q.id,
                            correctAnswerIds = listOf("1")
                        )
                    }
                }
            )
        }

        Assert.assertEquals(countedScore, dao.getCurrentSessionScore(quizId))

    }

}