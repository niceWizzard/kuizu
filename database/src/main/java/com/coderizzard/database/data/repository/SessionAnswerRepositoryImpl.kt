package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.session.answer.SessionAnswer
import com.coderizzard.database.data.database.dao.SessionAnswerDao
import com.coderizzard.database.domain.repository.SessionAnswerRepository
import javax.inject.Inject

class SessionAnswerRepositoryImpl @Inject constructor(
    private val sessionAnswerDao: SessionAnswerDao
) : SessionAnswerRepository {
    override suspend fun createAnswer(answer: SessionAnswer) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAnswer(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFromSession(sessionId: String): List<SessionAnswer> {
        TODO("Not yet implemented")
    }
}