package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.session.QuizSession

interface SessionRepository {
    suspend fun createSession(quizId : String)

    suspend fun getSession(quizId: String) : QuizSession

    suspend fun deleteSession(quizId: String)

    suspend fun getAll() : List<QuizSession>
}