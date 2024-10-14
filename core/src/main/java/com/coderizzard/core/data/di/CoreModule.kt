package com.coderizzard.core.data.di

import com.coderizzard.core.data.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun providesNavigationManager(): NavigationManager {
        return NavigationManager()
    }
}