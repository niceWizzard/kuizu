package com.coderizzard.network.domain

import com.coderizzard.network.data.model.ExtractedQuiz
import com.coderizzard.network.data.repository.ApiResponse
import retrofit2.http.Path

interface ExtractedQuizRepository {

    suspend fun extractQuizRaw(id : String) : ApiResponse<String>

    suspend fun extractQuizById(quizId: String) : ApiResponse<ExtractedQuiz>


}