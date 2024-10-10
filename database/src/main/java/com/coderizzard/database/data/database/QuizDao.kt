package com.coderizzard.database.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.database.data.database.model.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM QuizEntity")
    fun getAll() : Flow<List<QuizEntity>>

    @Insert
    suspend fun createQuiz(q : QuizEntity)

    @Query("SELECT * FROM QuizEntity AS ent WHERE ent.id = :id")
    fun getById(id: String) : Flow<QuizEntity>

}