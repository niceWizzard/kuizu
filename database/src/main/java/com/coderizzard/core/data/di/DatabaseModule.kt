package com.coderizzard.core.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coderizzard.core.data.DbConverter
import com.coderizzard.core.data.database.AppDatabase
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
}