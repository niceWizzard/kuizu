package com.coderizzard.database.domain.repository

import com.coderizzard.core.ResultState
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.session.QuizSession
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun createSession(quizId : String)

    suspend fun getSession(quizId: String) : ResultState<QuizSession>

    suspend fun deleteSession(quizId: String)

    fun getAll() : Flow<AsyncData<List<QuizSession>>>
    suspend fun hasActiveSession(quizId: String) : Boolean
}