package com.coderizzard.database.domain.repository

import com.coderizzard.core.ResultState
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.core.data.model.session.answer.MCQuestionAnswer
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun createSession(quizId : String)

    suspend fun getSession(quizId: String) : ResultState<QuizSession>

    suspend fun deleteSession(quizId: String)

    suspend fun getCurrentScore(quizId: String) : Int

    fun getAll() : Flow<AsyncData<List<QuizSession>>>
    suspend fun hasActiveSession(quizId: String) : Boolean

    suspend fun createQuestionAnswer(answer : MCQuestionAnswer)
    suspend fun createQuestionAnswer(answer : IdentificationAnswer)

    suspend fun retrySession(quizId: String){
        deleteSession(quizId)
        createSession(quizId)
    }
}