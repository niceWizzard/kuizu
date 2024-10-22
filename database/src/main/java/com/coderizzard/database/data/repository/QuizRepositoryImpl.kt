package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.database.data.database.QuestionDao
import com.coderizzard.database.data.database.QuizDao
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import com.coderizzard.database.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao,
    private val questionRepository: QuestionRepository,
    private val questionDao: QuestionDao
) : QuizRepository {
    override suspend fun createQuiz(
        quiz : Quiz
    ): String {
        val id = UUID.randomUUID().toString()
        quizDao.createQuiz(
            QuizEntity(
                id = id,
                name = quiz.name,
                createdAt = quiz.createdAt,
                author = quiz.author,
                imageLink = quiz.imageLink,
                remoteId = quiz.remoteId,
            ),
            questions = quiz.questions.map {
                when(it) {
                    is IdentificationQuestion -> IdentificationQuestionEntity(
                        quizId = id,
                        remoteId = it.remoteId,
                        answer = it.answer,
                        text = it.text,
                        point = it.point,
                    )
                    is MCQuestion -> MCQuestionEntity(
                        quizId = id,
                        point = it.point,
                        options = it.options,
                        answer = it.answer,
                        text = it.text,
                        remoteId = it.remoteId,
                    )
                }
            },
            questionDao = questionDao,
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