package com.coderizzard.network.domain

import com.coderizzard.network.data.repository.ApiResponse
import retrofit2.http.GET

interface ExtractedQuizRepository {

    @GET("quiz/{id}")
    suspend fun extractQuizRaw(id : String) : ApiResponse<String>

}