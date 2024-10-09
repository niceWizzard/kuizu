package com.coderizzard.database.data.repository

import com.coderizzard.database.data.database.QuizDao
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao
) : QuizRepository {
    override suspend fun createQuiz(q: QuizEntity) {
        quizDao.createQuiz(q)
    }

    override fun getAll(): Flow<List<QuizEntity>> {
        return quizDao.getAll()
    }
}