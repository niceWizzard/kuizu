package com.coderizzard.network.domain

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface QuizExtractorApi {

    @GET("quiz/{id}")
    suspend fun getQuizByIdRaw(@Path("id") quizId : String) : Response<ResponseBody>

}