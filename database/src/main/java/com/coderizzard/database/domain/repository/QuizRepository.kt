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

    suspend fun isRemoteIdUsed(remoteId : String) : Boolean

    suspend fun createQuiz(
        quiz : Quiz
    ) : String
}