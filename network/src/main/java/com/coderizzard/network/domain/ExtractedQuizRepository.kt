package com.coderizzard.network.domain

import retrofit2.http.GET

interface ExtractedQuizRepository {

    @GET("quiz/{id}")
    suspend fun extractQuizRaw(id : String) : String

}