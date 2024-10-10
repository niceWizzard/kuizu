package com.coderizzard.database.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MultipleChoiceQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao  {
    @Query("SELECT * FROM MultipleChoiceQuestionEntity AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizMCQuestions(quizId : String) : Flow<List<MultipleChoiceQuestionEntity>>

    @Query("SELECT * FROM IdentificationQuestionEntity AS ENT " +
            "WHERE ENT.quiz_id = :quizId")
    fun getQuizIdentificationQuestions(quizId : String) : Flow<List<IdentificationQuestionEntity>>

    @Insert
    suspend fun createQuestion(q : MultipleChoiceQuestionEntity)

    @Insert
    suspend fun createQuestion(q : IdentificationQuestionEntity)
}