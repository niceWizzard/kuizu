package com.coderizzard.network.domain

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.repository.ApiResponse
import retrofit2.http.Path

interface ExtractedQuizRepository {

    suspend fun extractQuizById(quizId: String) : ApiResponse<Quiz>
}