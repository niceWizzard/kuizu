package com.coderizzard.database.domain.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface QuizRepository  {
    suspend fun getAll() : Flow<List<Quiz>>

    suspend fun getById(id : String): Quiz

    suspend fun deleteQuiz(id : String)

    suspend fun createQuiz(
        name: String,
        author: String,
        createdAt: LocalDateTime,
        imageLink: String,
        remoteId : String,
        questionListBuilder : (id : String) -> List<QuestionEntity>
    )
}