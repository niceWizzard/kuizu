package com.coderizzard.database.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCOptionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity

@Dao
interface QuestionDao  {
    @Query(
        "SELECT * FROM mc_question AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizMCQuestions(quizId : String) : List<MCQuestionEntity>

    @Query("SELECT * FROM identification_question AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizIdentificationQuestions(quizId : String) : List<IdentificationQuestionEntity>

    @Query("SELECT * FROM mc_option WHERE question_id = :id")
    suspend fun getMCQuestionOptions(id : String) : List<MCOptionEntity>

    @Insert
    suspend fun createQuestion(q : MCQuestionEntity)

    @Insert
    suspend fun createQuestion(q : IdentificationQuestionEntity)

    @Insert
    suspend fun createMCOption(option : MCOptionEntity)
}