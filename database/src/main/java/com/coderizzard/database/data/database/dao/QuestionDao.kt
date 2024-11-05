package com.coderizzard.database.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.core.data.model.question.UnsupportedQuestion
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCOptionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.data.database.model.question.UnsupportedQuestionEntity

@Dao
interface QuestionDao  {
    @Query(
        "SELECT * FROM mc_question AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizMCQuestions(quizId : String) : List<MCQuestionEntity>

    @Query("SELECT * FROM identification_question AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizIdentificationQuestions(quizId : String) : List<IdentificationQuestionEntity>

    @Query("""
        SELECT * FROM unsupported_question WHERE quiz_id = :quizId
    """)
    suspend fun getQuizUnsupportedQuestions(quizId : String) : List<UnsupportedQuestionEntity>

    @Query("SELECT * FROM mc_option WHERE question_id = :id")
    suspend fun getMCQuestionOptions(id : String) : List<MCOptionEntity>

    @Insert
    suspend fun createQuestion(q : MCQuestionEntity)
    @Insert
    suspend fun createQuestion(q : UnsupportedQuestionEntity)

    @Insert
    suspend fun createQuestion(q : IdentificationQuestionEntity)

    @Insert
    suspend fun createMCOption(option : MCOptionEntity)
}