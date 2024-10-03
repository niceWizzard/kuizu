package com.coderizzard.network.data

import android.content.Context
import com.coderizzard.network.data.repository.ExtractedQuizRepositoryImpl
import com.coderizzard.network.domain.ExtractedQuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://quizizz.com/")
            .build()
    }

    @Provides
    @ActivityScoped
    fun providesExtractorRepository(retrofit: Retrofit) : ExtractedQuizRepository {
        return retrofit.create(ExtractedQuizRepositoryImpl::class.java)
    }

}