package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.database.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao,
    private val quizRepository: QuizRepository,
) : SessionRepository {
    override suspend fun createSession(quizId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSession(quizId: String): QuizSession {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSession(quizId: String) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<QuizSession>> {
        return sessionDao.getAll().map { sessionList ->
            sessionList.map { sessionEntity ->
                val quiz = quizRepository.getById(sessionEntity.quizId)
                sessionEntity.toQuizSession(quiz)
            }
        }
    }
}