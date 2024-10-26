package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.model.session.answer.SessionAnswer

interface SessionAnswerRepository {
    suspend fun createAnswer(answer : SessionAnswer)

    suspend fun deleteAnswer(id : String)

    suspend fun getAllFromSession(sessionId : String) : List<SessionAnswer>

}