package com.coderizzard.network.data.repository

import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.network.domain.QuizExtractorApi
import javax.inject.Inject

class ExtractedQuizRepositoryImpl @Inject constructor(
    private val extractorApi: QuizExtractorApi
) : ExtractedQuizRepository {
    override suspend fun extractQuizRaw(id: String): String {
        return try {
            val response = extractorApi.getQuizByIdRaw(id)
            if (response.isSuccessful) {
                response.body()?.string() ?: "Null"  // Correctly reading the body as a string
            } else {
                "Error: ${response.code()}"
            }
        } catch (e: Exception) {
            "Some error happened: \n${e.message}"
        }
    }
}