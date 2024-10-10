package com.coderizzard.database.data.repository

import com.coderizzard.core.data.model.question.Question
import com.coderizzard.database.data.database.QuestionDao
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MultipleChoiceQuestionEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao,
) : QuestionRepository {
    override fun getAllByQuizId(quizId: String): Flow<List<Question>> {
        return questionDao.getQuizMCQuestions(quizId).combine( questionDao.getQuizIdentificationQuestions(quizId)) { a,b ->
            a + b
        }.map { list ->
           list.map {
               when(it) {
                   is IdentificationQuestionEntity -> it.toIdentificationQuestion()
                   is MultipleChoiceQuestionEntity -> it.toMCQuestion()
               }
           }
        }
    }
}