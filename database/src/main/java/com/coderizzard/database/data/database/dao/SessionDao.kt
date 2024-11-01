package com.coderizzard.database.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.answers.IdentificationAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.MCQuestionAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Dao
interface SessionDao {

    @Query("SELECT EXISTS(SELECT 1 FROM quiz_session WHERE quiz_id = :quizId)")
    suspend fun hasActiveSession(quizId : String) : Boolean

    @Query("DELETE FROM quiz_session WHERE quiz_id = :quizId")
    suspend fun deleteActiveSession(quizId: String)

    @Query("""
        SELECT quiz_session.*, 
           (
                (SELECT COUNT(*) FROM identification_answer WHERE identification_answer.quiz_id = quiz_session.quiz_id)
                + (SELECT COUNT(*) FROM mc_question_answer WHERE mc_question_answer.quiz_id = quiz_session.quiz_id)
           ) AS current_question_index  
        FROM quiz_session 
        WHERE quiz_id =:quizId
        GROUP BY quiz_session.quiz_id
    """
    )
    suspend fun getActiveSession(quizId: String) : QuizSessionEntity

    @Insert
    suspend fun createSession(sessionEntity: QuizSessionEntity)

    @Query("""
        SELECT quiz_session.*, 
           (
                (SELECT COUNT(*) FROM identification_answer WHERE identification_answer.quiz_id = quiz_session.quiz_id)
                + (SELECT COUNT(*) FROM mc_question_answer WHERE mc_question_answer.quiz_id = quiz_session.quiz_id)
           ) AS current_question_index  
        FROM quiz_session 
        GROUP BY quiz_session.quiz_id
        ORDER BY started_at ASC
    """)
    fun getAll() : Flow<List<QuizSessionEntity>>

    @Insert
    suspend fun createQuestionAnswer(q : MCQuestionAnswerEntity)
    @Insert
    suspend fun createQuestionAnswer(q : IdentificationAnswerEntity)
}