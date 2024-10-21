package com.coderizzard.database.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MultipleChoiceQuestionEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM QuizEntity")
    fun getAll() : Flow<List<QuizEntity>>

    @Insert
    suspend fun createQuiz(q : QuizEntity)

    @Transaction
    suspend fun createQuiz(q : QuizEntity, questions : List<QuestionEntity>, questionDao : QuestionDao) {
        createQuiz(q)
        questions.forEach { question ->
            when(question) {
                is IdentificationQuestionEntity -> {
                    questionDao.createQuestion(question)
                }
                is MultipleChoiceQuestionEntity -> {
                    questionDao.createQuestion(question)
                }
            }
        }
    }

    @Query("SELECT * FROM QuizEntity AS ent WHERE ent.id = :id")
    suspend fun getById(id: String) : QuizEntity

    @Query("DELETE FROM QuizEntity WHERE id = :id")
    fun deleteQuiz(id : String)
}