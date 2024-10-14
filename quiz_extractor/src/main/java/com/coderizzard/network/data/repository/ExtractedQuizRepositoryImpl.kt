package com.coderizzard.network.data.repository

import com.coderizzard.network.data.model.ExtractedQuiz
import com.coderizzard.network.data.util.resolveQuizId
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.network.domain.QuizExtractorApi
import com.google.gson.Gson
import com.google.gson.JsonParser
import retrofit2.HttpException
import javax.inject.Inject

class ExtractedQuizRepositoryImpl @Inject constructor(
    private val extractorApi: QuizExtractorApi
) : ExtractedQuizRepository {

    override suspend fun extractQuizById(quizId: String): ApiResponse<ExtractedQuiz> {
        return try {
            val id = resolveQuizId(quizId) ?: throw Exception("Invalid id or url given")
            ApiResponse.Success(extractorApi.extractQuizById(
                id
            ))
        } catch (e : HttpException) {
            try {
                if(e.code() == 400) {
                    val body = e.response()?.errorBody()
                    if(body != null) {
                        val bodyJson = JsonParser.parseString(body.string()).asJsonObject
                        val message = bodyJson.get("message").asString
                        return ApiResponse.Error("Quizizz error: $message")
                    }
                }
                ApiResponse.Error("Some error happened: \n${e}")
            } catch (e : Exception) {
                ApiResponse.Error("Some error happened: \n${e}")
            }
        } catch (e : Exception) {
            ApiResponse.Error("Some error happened: \n${e}")
        }
    }
}