package com.coderizzard.database.data.repository

import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.database.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao,
    private val quizRepository: QuizRepository,
) : SessionRepository {
    override suspend fun createSession(quizId: String) = withContext(Dispatchers.IO) {
        val quiz = quizRepository.getById(quizId)
        val questionOrder = quiz.questions.shuffled().map { question ->
            question.id
        }
        sessionDao.createSession(
            QuizSessionEntity(
                quizId = quizId,
                startedAt = LocalDateTime.now(),
                questionOrder = questionOrder ,
                id = UUID.randomUUID().toString(),
            )
        )
    }

    override suspend fun getSession(quizId: String): QuizSession {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSession(quizId: String) {
        TODO("Not yet implemented")
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
}