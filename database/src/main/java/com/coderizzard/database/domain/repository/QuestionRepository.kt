package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.question.Question
import com.coderizzard.database.data.database.model.question.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getAllByQuizId(quizId : String) : Flow<List<Question>>
    suspend fun createQuestion(question : QuestionEntity)
}