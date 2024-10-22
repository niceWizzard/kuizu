package com.coderizzard.network.domain

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.repository.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface QuizExtractorApi {


    @GET("quiz/{id}")
    suspend fun extractQuizById(@Path("id") quizId: String) : Quiz

}