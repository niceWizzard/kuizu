package com.coderizzard.quiz.data

import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.quiz.domain.repository.ImageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerModule {
    @Provides
    @Singleton
    fun providesImageManager(
        extractorRepository : ExtractedQuizRepository
    ) : ImageManager {
        return ImageManagerImpl(
            extractorRepository
        )
    }
}