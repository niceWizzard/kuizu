package com.coderizzard.network.data

import com.coderizzard.network.data.repository.ExtractedQuizRepositoryImpl
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.network.domain.QuizExtractorApi
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

        return Retrofit.Builder()
            .baseUrl("https://quizizz.com/")
            .addConverterFactory(GsonConverterFactory.create())
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