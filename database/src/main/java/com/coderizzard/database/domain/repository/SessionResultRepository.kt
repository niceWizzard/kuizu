package com.coderizzard.database.domain.repository

import com.coderizzard.core.ResultState
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.core.data.model.session.SessionResultWithUserAnswers

interface SessionResultRepository {
    suspend fun getLatestResult(quizId : String) : ResultState<SessionResultWithUserAnswers>

    suspend fun createResult(result: SessionResult)

}