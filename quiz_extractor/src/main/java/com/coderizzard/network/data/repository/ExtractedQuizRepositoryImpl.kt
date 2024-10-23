package com.coderizzard.network.data.repository

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.util.resolveQuizId
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.network.domain.QuizExtractorApi
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ExtractedQuizRepositoryImpl @Inject constructor(
    private val extractorApi: QuizExtractorApi
) : ExtractedQuizRepository {

    override suspend fun extractQuizById(quizId: String): ApiResponse<Quiz> {
        return try {
            val id = resolveQuizId(quizId) ?: throw InvalidQuizizzIdOrUrl(quizId)
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

    override suspend fun extractImage(imageUrl : String): ApiResponse<Response<ResponseBody>> {
        return try {
             ApiResponse.Success(extractorApi.downloadImage(imageUrl))
        }catch (e : Exception) {
            ApiResponse.Error(
                message = e.message.toString()
            )
        }
    }
}

class InvalidQuizizzIdOrUrl(idOrURl : String) : Exception("Invalid id or url given. Received: ${idOrURl}")