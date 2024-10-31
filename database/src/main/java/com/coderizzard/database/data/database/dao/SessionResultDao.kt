package com.coderizzard.database.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.database.data.database.model.session.SessionResultEntity

@Dao
interface SessionResultDao {
    @Query("""
        SELECT * 
        FROM session_result 
        WHERE quiz_id = :quizId 
        ORDER BY date_finished DESC
    """)
    suspend fun getAllResult(quizId : String) : List<SessionResultEntity>

    @Insert
    suspend fun createResult(result : SessionResultEntity)
    @Query("""
        SELECT * 
        FROM session_result 
        WHERE quiz_id = :quizId 
        ORDER BY date_finished DESC
        LIMIT 1
    """)
    suspend fun getLatestResult(quizId: String) : SessionResultEntity

}