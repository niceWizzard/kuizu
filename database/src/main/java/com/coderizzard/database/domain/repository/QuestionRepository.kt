package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.question.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getAllByQuizId(quizId : String) : Flow<List<Question>>
}