package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.database.data.database.QuestionDao
import com.coderizzard.database.data.database.QuizDao
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import com.coderizzard.database.domain.repository.QuizRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao,
    private val questionRepository: QuestionRepository,
) : QuizRepository {
    override suspend fun createQuiz(q: QuizEntity) {
        quizDao.createQuiz(q)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAll(): Flow<List<Quiz>> {
        return quizDao.getAll().flatMapConcat { quizList ->
            val quizzesFlow = quizList.map { quizEntity ->
                questionRepository.getAllByQuizId(quizEntity.id).map { q ->
                    quizEntity.toQuiz(q)
                }
            }
            combine(quizzesFlow){it.toList()}
        }
    }
}