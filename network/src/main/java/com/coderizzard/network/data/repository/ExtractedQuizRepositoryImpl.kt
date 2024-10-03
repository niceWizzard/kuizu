package com.coderizzard.network.data.repository

import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.network.domain.QuizExtractorApi
import javax.inject.Inject

class ExtractedQuizRepositoryImpl @Inject constructor(
    private val extractorApi: QuizExtractorApi
) : ExtractedQuizRepository {
    override suspend fun extractQuizRaw(id: String): ApiResponse<String> {
        return try {
            val response = extractorApi.getQuizByIdRaw(id)
            if (response.isSuccessful) {
                ApiResponse.Success(response.body()?.string() ?: "Null")
            } else {
                ApiResponse.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Some error happened: \n${e.message}")
        }
    }
}