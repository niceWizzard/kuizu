package com.coderizzard.network.domain

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.repository.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url


interface QuizExtractorApi {


    @GET("quiz/{id}")
    suspend fun extractQuizById(@Path("id") quizId: String) : Quiz

    @Streaming
    @GET
    suspend fun downloadImage(@Url imageUrl : String) : Response<ResponseBody>

}