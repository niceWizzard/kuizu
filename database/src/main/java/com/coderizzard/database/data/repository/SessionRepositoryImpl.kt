package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao,
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

    override suspend fun getAll(): List<QuizSession> {
        TODO("Not yet implemented")
    }
}