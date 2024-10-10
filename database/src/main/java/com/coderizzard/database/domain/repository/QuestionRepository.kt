package com.coderizzard.database.domain.repository

import com.coderizzard.quiz.data.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getAllByQuizId(quizId : String) : Flow<List<Question>>
}