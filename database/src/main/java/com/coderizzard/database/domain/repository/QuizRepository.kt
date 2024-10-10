package com.coderizzard.database.domain.repository

import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.quiz.data.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepository  {
    suspend fun createQuiz(q : QuizEntity)
    fun getAll() : Flow<List<Quiz>>
}