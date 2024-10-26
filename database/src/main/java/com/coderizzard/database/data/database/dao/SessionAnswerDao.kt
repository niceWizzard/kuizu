package com.coderizzard.database.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.coderizzard.database.data.database.model.session.answers.IdentificationAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.MCQuestionAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer

@Dao
interface SessionAnswerDao {
    @Query("SELECT * FROM identification_answer WHERE session_id = :sessionId")
    suspend fun getAllIdentificationFromSession(sessionId : String)  : List<IdentificationAnswerEntity>

    @Query("SELECT * FROM mc_question_answer WHERE session_id = :sessionId")
    suspend fun getAllMCQuestionAnswerFromSession(sessionId : String)  : List<MCQuestionAnswerEntity>

    @Transaction
    suspend fun getAllAnswersFromSession(sessionId : String) : List<SessionAnswer> {
        return getAllIdentificationFromSession(sessionId) + getAllMCQuestionAnswerFromSession(sessionId)
    }

    @Insert
    suspend fun createSessionAnswer(answer : IdentificationAnswerEntity)

    @Insert
    suspend fun createSessionAnswer(answer : MCQuestionAnswerEntity)

    suspend fun createSessionAnswer(answer : SessionAnswer) {
        when(answer) {
            is IdentificationAnswerEntity -> {createSessionAnswer(answer)}
            is MCQuestionAnswerEntity -> {createSessionAnswer(answer)}
        }
    }

    @Query("DELETE FROM mc_question_answer WHERE session_id = :sessionId")
    suspend fun deleteAllMCQuestionAnswer(sessionId : String)

    @Query("DELETE FROM identification_answer WHERE session_id = :sessionId")
    suspend fun deleteAllIdentificationAnswer(sessionId : String)
}