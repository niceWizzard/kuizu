package com.coderizzard.network.data

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.repository.ExtractedQuizRepositoryImpl
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.network.domain.QuizExtractorApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(Quiz::class.java, QuizJsonDeserializer())
            .create()
        val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://quizizz.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesQuizExtractorApi(retrofit: Retrofit) : QuizExtractorApi {
        return retrofit.create(QuizExtractorApi::class.java)
    }

    @Provides
    @Singleton
    fun providesExtractorRepository(api : QuizExtractorApi) : ExtractedQuizRepository {
        return ExtractedQuizRepositoryImpl(api)
    }

}