package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.database.data.database.QuizDao
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import com.coderizzard.database.domain.repository.QuizRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao,
    private val questionRepository: QuestionRepository,
) : QuizRepository {
    override suspend fun createQuiz(
        name: String,
        author: String,
        createdAt: LocalDateTime,
        imageLink: String
    ): String {
        val id = UUID.randomUUID().toString()
        quizDao.createQuiz(
            QuizEntity(
                id = id,
                name = name,
                createdAt = createdAt,
                author = author,
                imageLink = imageLink,
            )
        )
        return id

    }

    override suspend fun getAll(): Flow<List<Quiz>> {
        return quizDao.getAll().map { list ->
            list.map { it.toQuiz(
                questionRepository.getAllByQuizId(it.id)
            ) }
        }
    }

    override suspend fun getById(id: String) : Quiz {
        return quizDao.getById(id).toQuiz(questionRepository.getAllByQuizId(id))
    }

    override suspend fun deleteQuiz(id: String) {
        quizDao.deleteQuiz(id)
    }
}