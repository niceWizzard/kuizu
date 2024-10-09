package com.coderizzard.database.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.coderizzard.database.data.DbConverter
import com.coderizzard.database.data.database.AppDatabase
import com.coderizzard.database.data.database.QuizDao
import com.coderizzard.database.data.repository.QuizRepositoryImpl
import com.coderizzard.database.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@TypeConverters(
    DbConverter::class
)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context : Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "quizzerist.db"
        ).allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providesQuizDao(appDb : AppDatabase): QuizDao {
        return appDb.quizDao
    }

    @Provides
    @Singleton
    fun providesQuizRepository(quizDao: QuizDao) : QuizRepository {
        return QuizRepositoryImpl(quizDao)
    }
}