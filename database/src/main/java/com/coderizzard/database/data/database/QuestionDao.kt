package com.coderizzard.database.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity

@Dao
interface QuestionDao  {
    @Query(
        "SELECT * FROM MCQuestionEntity AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizMCQuestions(quizId : String) : List<MCQuestionEntity>

    @Query("SELECT * FROM IdentificationQuestionEntity AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizIdentificationQuestions(quizId : String) : List<IdentificationQuestionEntity>

    @Insert
    suspend fun createQuestion(q : MCQuestionEntity)

    @Insert
    suspend fun createQuestion(q : IdentificationQuestionEntity)
}