package com.coderizzard.database.data.repository

import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao,
) : SessionRepository {
}