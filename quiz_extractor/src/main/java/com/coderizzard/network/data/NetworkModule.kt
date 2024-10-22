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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

        return Retrofit.Builder()
            .baseUrl("https://quizizz.com/")
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