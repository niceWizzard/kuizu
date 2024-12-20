package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.question.Question
import com.coderizzard.database.data.database.dao.QuestionDao
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import com.coderizzard.database.data.database.model.question.UnsupportedQuestionEntity
import com.coderizzard.database.data.database.model.question.toEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao,
) : QuestionRepository {
    override suspend fun getAllByQuizId(quizId: String): List<Question> {
        return withContext(Dispatchers.IO) {
            val a = questionDao.getQuizUnsupportedQuestions(quizId).map { it.toUnsupportedQuestion() }
            questionDao.getQuizMCQuestions(quizId).map { q ->
                val options = questionDao.getMCQuestionOptions(q.id).map{opt -> opt.toMCOption()}
                q.toMCQuestion(options)
            } + questionDao.getQuizIdentificationQuestions(quizId).map { it.toIdentificationQuestion() } +
            a
        }
    }

    override suspend fun createQuestion(question: QuestionEntity) {
        withContext(Dispatchers.IO) {
            when(question) {
                is IdentificationQuestionEntity -> questionDao.createQuestion(question)
                is MCQuestionEntity -> questionDao.createQuestion(question)
                is UnsupportedQuestionEntity -> questionDao.createQuestion(question)
            }
        }
    }
}