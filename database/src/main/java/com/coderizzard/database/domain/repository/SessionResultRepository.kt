package com.coderizzard.database.domain.repository

import com.coderizzard.core.ResultState
import com.coderizzard.core.data.model.session.SessionResult

interface SessionResultRepository {
    suspend fun getLatestResult(quizId : String) : ResultState<SessionResult>

    suspend fun createResult(result: SessionResult)

}