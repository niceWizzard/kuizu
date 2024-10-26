package com.coderizzard.database.data.repository

import com.coderizzard.database.data.database.dao.SessionAnswerDao
import com.coderizzard.database.domain.repository.SessionAnswerRepository
import javax.inject.Inject

class SessionAnswerRepositoryImpl @Inject constructor(
    private val sessionAnswerDao: SessionAnswerDao
) : SessionAnswerRepository {
}