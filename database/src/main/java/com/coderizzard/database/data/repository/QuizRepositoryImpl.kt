package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.question.UnsupportedQuestion
import com.coderizzard.database.data.database.dao.QuestionDao
import com.coderizzard.database.data.database.dao.QuizDao
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCOptionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.data.database.model.question.toEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import com.coderizzard.database.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
        return withContext(Dispatchers.IO) {
            val id = UUID.randomUUID().toString()
            val options = mutableListOf<MCOptionEntity>()
            quizDao.createQuiz(
                QuizEntity(
                    id = id,
                    name = quiz.name,
                    createdAt = quiz.createdAt,
                    author = quiz.author,
                    imageLink = quiz.imageLink,
                    remoteId = quiz.remoteId,
                    localImagePath = quiz.localImagePath
                ),
                questions = quiz.allQuestions.map {
                    when(it) {
                        is IdentificationQuestion -> IdentificationQuestionEntity(
                            quizId = id,
                            remoteId = it.remoteId,
                            answer = it.answer,
                            text = it.text,
                            point = it.point,
                            localImagePath = it.localImagePath,
                            imageLink = it.imageLink,
                        )
                        is MCQuestion -> {
                            val questionId = UUID.randomUUID().toString()
                            options.addAll(
                                it.options.map { opt ->
                                    MCOptionEntity(
                                        questionId =  questionId,
                                        text = opt.text,
                                        remoteId = opt.remoteId,
                                    )
                                }
                            )
                            MCQuestionEntity(
                                id = questionId,
                                quizId = id,
                                point = it.point,
                                answer = it.answer,
                                text = it.text,
                                remoteId = it.remoteId,
                                localImagePath = it.localImagePath,
                                imageLink = it.imageLink,
                            )
                        }

                        is UnsupportedQuestion -> {
                            it.toEntity().copy(quizId = id)
                        }
                    }
                },
                questionDao = questionDao,
                options = options
            )
            id
        }
    }

    override suspend fun getAll(): Flow<List<Quiz>> {
        return quizDao.getAll().map { list ->
            list.map { it.toQuiz(
                questionRepository.getAllByQuizId(it.id)
            ) }
        }
    }

    override suspend fun getById(id: String) : Quiz {
        return withContext(Dispatchers.IO ) {
            quizDao.getById(id).toQuiz(questionRepository.getAllByQuizId(id))
        }
    }

    override suspend fun deleteQuiz(id: String) {
        withContext(Dispatchers.IO) {
            quizDao.deleteQuiz(id)
        }
    }

    override suspend fun isRemoteIdUsed(remoteId: String): Boolean {
        return withContext(Dispatchers.IO) {
            quizDao.isRemoteIdUsed(remoteId)
        }
    }
}