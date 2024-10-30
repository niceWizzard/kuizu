package com.coderizzard.database.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.answers.IdentificationAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.MCQuestionAnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT EXISTS(SELECT 1 FROM quiz_session WHERE quiz_id = :quizId)")
    suspend fun hasActiveSession(quizId : String) : Boolean

    @Query("DELETE FROM quiz_session WHERE quiz_id = :quizId")
    suspend fun deleteActiveSession(quizId: String)

    @Query("""
        SELECT quiz_session.*, 
           (
            COUNT(identification_answer.question_id)
            +  COUNT(mc_question_answer.question_id)
            ) AS current_question_index 
        FROM quiz_session 
        LEFT JOIN identification_answer ON quiz_session.quiz_id = identification_answer.quiz_id 
        LEFT JOIN mc_question_answer ON quiz_session.quiz_id = mc_question_answer.quiz_id
        WHERE quiz_session.quiz_id = :quizId
        GROUP BY quiz_session.quiz_id
    """
    )
    suspend fun getActiveSession(quizId: String) : QuizSessionEntity

    @Insert
    suspend fun createSession(sessionEntity: QuizSessionEntity)

    @Query("""
        SELECT quiz_session.*, 
           (
            COUNT(identification_answer.question_id)
            +  COUNT(mc_question_answer.question_id)
            ) AS current_question_index  
        FROM quiz_session 
        LEFT JOIN identification_answer ON quiz_session.quiz_id = identification_answer.quiz_id 
        LEFT JOIN mc_question_answer ON quiz_session.quiz_id = mc_question_answer.quiz_id
        GROUP BY quiz_session.quiz_id
        ORDER BY started_at ASC
    """)
    fun getAll() : Flow<List<QuizSessionEntity>>

    @Insert
    suspend fun createQuestionAnswer(q : MCQuestionAnswerEntity)
    @Insert
    suspend fun createQuestionAnswer(q : IdentificationAnswerEntity)
}