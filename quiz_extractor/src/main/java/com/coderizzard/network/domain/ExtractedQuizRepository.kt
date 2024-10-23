package com.coderizzard.network.domain

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.repository.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Path

interface ExtractedQuizRepository {

    suspend fun extractQuizById(quizId: String) : ApiResponse<Quiz>
    suspend fun extractImage(imageUrl: String) : ApiResponse<Response<ResponseBody>>
}