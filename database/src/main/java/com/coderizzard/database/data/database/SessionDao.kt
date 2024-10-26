package com.coderizzard.database.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT EXISTS(SELECT 1 FROM quiz_session WHERE quiz_id = :quizId)")
    suspend fun hasActiveSession(quizId : String) : Boolean

    @Query("SELECT * FROM quiz_session WHERE quiz_id = :quizId")
    suspend fun getActiveSession(quizId: String) : QuizSessionEntity

    @Query("DELETE FROM quiz_session WHERE quiz_id = :quizId")
    suspend fun deleteActiveSession(quizId: String)

    @Insert
    suspend fun createSession(sessionEntity: QuizSessionEntity)

    @Query("SELECT * FROM quiz_session ORDER BY started_at ASC")
    fun getAll() : Flow<List<QuizSessionEntity>>

}