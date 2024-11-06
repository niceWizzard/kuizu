package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.core.data.model.session.SessionResultWithUserAnswers

interface SessionResultRepository {
    suspend fun getLatestResult(quizId : String) : Result<SessionResultWithUserAnswers>

    suspend fun createResult(result: SessionResult)

}