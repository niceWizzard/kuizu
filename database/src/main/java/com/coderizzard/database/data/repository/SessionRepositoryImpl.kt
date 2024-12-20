package com.coderizzard.database.data.repository

import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.core.data.model.session.answer.MCQuestionAnswer
import com.coderizzard.database.data.database.dao.SessionAnswerDao
import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.answers.toEntity
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.database.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao,
    private val quizRepository: QuizRepository,
    private val sessionAnswerDao: SessionAnswerDao,
) : SessionRepository {
    override suspend fun createSession(quizId: String) = withContext(Dispatchers.IO) {
        val quiz = quizRepository.getById(quizId)
        val questionOrder = quiz.supportedQuestions.shuffled().map { question ->
            question.id
        }
        sessionDao.createSession(
            QuizSessionEntity(
                quizId = quizId,
                startedAt = LocalDateTime.now(),
                questionOrder = questionOrder ,
            )
        )
    }

    override suspend fun getSession(quizId: String): Result<QuizSession> {
        return try {
            val quiz = quizRepository.getById(quizId)
            Result.success(sessionDao.getActiveSession(quizId).toQuizSession(quiz))
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteSession(quizId: String) = withContext(Dispatchers.IO) {
        try {
            sessionDao.deleteActiveSession(quizId)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getCurrentScore(quizId: String) = withContext(Dispatchers.IO){
        sessionAnswerDao.getCurrentSessionScore(quizId)
    }

    override fun getAll(): Flow<AsyncData<List<QuizSession>>> {
        return sessionDao.getAll()
            .map { sessionList ->
            AsyncData.Success(
                sessionList.map { sessionEntity ->
                    val quiz = quizRepository.getById(sessionEntity.quizId)
                    sessionEntity.toQuizSession(quiz)
                }
            ) as AsyncData<List<QuizSession>>
        }.onStart { emit(AsyncData.Loading )}
            .catch { e ->
                AsyncData.Error(message = e.message ?: "null", error = e.cause)
            }
    }

    override suspend fun hasActiveSession(quizId: String): Boolean {
        return sessionDao.hasActiveSession(quizId)
    }

    override suspend fun createQuestionAnswer(answer: MCQuestionAnswer) {
        sessionDao.createQuestionAnswer(answer.toEntity())
    }

    override suspend fun createQuestionAnswer(answer: IdentificationAnswer) {
        sessionDao.createQuestionAnswer(answer.toEntity())
    }
}