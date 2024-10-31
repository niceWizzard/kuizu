package com.coderizzard.database.data.repository

import com.coderizzard.core.ResultState
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.database.data.database.dao.SessionResultDao
import com.coderizzard.database.data.database.model.session.toEntity
import com.coderizzard.database.domain.repository.SessionResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SessionResultRepositoryImpl @Inject constructor(
    private val sessionResultDao: SessionResultDao
) : SessionResultRepository {
    override suspend fun getLatestResult(quizId: String): ResultState<SessionResult> = withContext(Dispatchers.IO){
        try {
            val a = sessionResultDao.getLatestResult(quizId)
            ResultState.Success(a.toSessionResult())
        } catch (e : Exception) {
            ResultState.Error(e.message.toString(), e.cause)
        }
    }

    override suspend fun createResult(result: SessionResult) = withContext(Dispatchers.IO) {
        try {
            sessionResultDao.createResult(
                result.toEntity()
            )
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }
}