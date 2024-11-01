package com.coderizzard.database.data.di

import android.content.Context
import androidx.room.Room
import com.coderizzard.database.data.database.AppDatabase
import com.coderizzard.database.data.database.dao.QuestionDao
import com.coderizzard.database.data.database.dao.QuizDao
import com.coderizzard.database.data.database.dao.SessionAnswerDao
import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.data.database.dao.SessionResultDao
import com.coderizzard.database.data.repository.QuestionRepositoryImpl
import com.coderizzard.database.data.repository.QuizRepositoryImpl
import com.coderizzard.database.data.repository.SessionAnswerRepositoryImpl
import com.coderizzard.database.data.repository.SessionRepositoryImpl
import com.coderizzard.database.data.repository.SessionResultRepositoryImpl
import com.coderizzard.database.domain.repository.QuestionRepository
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.database.domain.repository.SessionAnswerRepository
import com.coderizzard.database.domain.repository.SessionRepository
import com.coderizzard.database.domain.repository.SessionResultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context : Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "kuizu.db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun providesQuizDao(appDb : AppDatabase): QuizDao {
        return appDb.quizDao
    }

    @Provides
    @Singleton
    fun providesQuestionDao(appDb : AppDatabase): QuestionDao {
        return appDb.questionDao
    }

    @Provides
    @Singleton
    fun providesSessionDao(appDb : AppDatabase): SessionDao {
        return appDb.sessionDao
    }

    @Provides
    @Singleton
    fun providesSessionAnswerDao(appDb : AppDatabase): SessionAnswerDao {
        return appDb.sessionAnswerDao
    }

    @Provides
    @Singleton
    fun providesSessionResultDao(appDb : AppDatabase): SessionResultDao {
        return appDb.sessionResultDao
    }

    @Provides
    @Singleton
    fun providesQuizRepository(
        quizDao: QuizDao, questionRepository: QuestionRepository,
        questionDao: QuestionDao
    ) : QuizRepository {
        return QuizRepositoryImpl(quizDao, questionRepository, questionDao)
    }

    @Provides
    @Singleton
    fun providesQuestionRepository(questionDao: QuestionDao) : QuestionRepository {
        return QuestionRepositoryImpl(
            questionDao,
        )
    }

    @Provides
    @Singleton
    fun providesSessionRepository(
        sessionDao: SessionDao,
        quizRepository: QuizRepository
    ) : SessionRepository {
        return SessionRepositoryImpl(
            sessionDao = sessionDao,
            quizRepository = quizRepository,
        )
    }

    @Provides
    @Singleton
    fun providesSessionAnswerRepository(
        sessionAnswerDao: SessionAnswerDao
    ) : SessionAnswerRepository {
        return SessionAnswerRepositoryImpl(
            sessionAnswerDao = sessionAnswerDao
        )
    }

    @Provides
    @Singleton
    fun providesSessionResultRepository(
        dao : SessionResultDao,
        quizRepository: QuizRepository,
        sessionAnswerDao : SessionAnswerDao,
        sessionDao: SessionDao,
    ): SessionResultRepository {
        return SessionResultRepositoryImpl(
            sessionResultDao = dao,
            quizRepository = quizRepository,
            sessionAnswerDao = sessionAnswerDao,
            sessionDao = sessionDao,
        )
    }
}