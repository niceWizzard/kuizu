package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.database.data.database.model.QuizEntity
import kotlinx.coroutines.flow.Flow

interface QuizRepository  {
    suspend fun createQuiz(q : QuizEntity)
    fun getAll() : Flow<List<Quiz>>
}