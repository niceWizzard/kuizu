package com.coderizzard.network.domain

import com.coderizzard.network.data.repository.ApiResponse

interface ExtractedQuizRepository {

    suspend fun extractQuizRaw(id : String) : ApiResponse<String>



}