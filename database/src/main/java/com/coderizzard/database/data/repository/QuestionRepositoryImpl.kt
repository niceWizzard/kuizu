package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.question.Question
import com.coderizzard.database.data.database.QuestionDao
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao,
) : QuestionRepository {
    override suspend fun getAllByQuizId(quizId: String): List<Question> {
        return questionDao.getQuizMCQuestions(quizId).map { it.toMCQuestion() } + questionDao.getQuizIdentificationQuestions(quizId).map { it.toIdentificationQuestion() }
    }

    override suspend fun createQuestion(question: QuestionEntity) {
        when(question) {
            is IdentificationQuestionEntity -> questionDao.createQuestion(question)
            is MCQuestionEntity -> questionDao.createQuestion(question)
        }
    }
}