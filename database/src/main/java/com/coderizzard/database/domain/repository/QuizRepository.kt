package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.database.data.database.model.QuizEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface QuizRepository  {
    suspend fun createQuiz(
        name : String,
        author : String,
        createdAt : LocalDateTime = LocalDateTime.now(),
        imageLink : String,
    ) : String
    fun getAll() : Flow<List<Quiz>>

    fun getById(id : String): Flow<Quiz>
}