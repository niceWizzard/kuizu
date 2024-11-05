package com.coderizzard.database.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCOptionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import com.coderizzard.database.data.database.model.question.UnsupportedQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz ORDER BY created_at ASC")
    fun getAll() : Flow<List<QuizEntity>>

    @Insert
    suspend fun createQuiz(q : QuizEntity)

    @Transaction
    suspend fun createQuiz(
        q : QuizEntity,
        questions : List<QuestionEntity>,
        questionDao : QuestionDao,
        options : List<MCOptionEntity>,
    ) {
        createQuiz(q)
        questions.forEach { question ->
            when(question) {
                is IdentificationQuestionEntity -> {
                    questionDao.createQuestion(question)
                }
                is MCQuestionEntity -> {
                    questionDao.createQuestion(question)
                }
                is UnsupportedQuestionEntity -> {
                    questionDao.createQuestion(question)
                }
            }
        }
        options.forEach { opt ->
            questionDao.createMCOption(opt)
        }
    }

    @Query("SELECT EXISTS(SELECT 1 FROM quiz WHERE remote_id = :remoteId )")
    suspend fun isRemoteIdUsed(remoteId : String) : Boolean

    @Query("SELECT * FROM quiz WHERE id = :id")
    suspend fun getById(id: String) : QuizEntity

    @Query("DELETE FROM quiz WHERE id = :id")
    fun deleteQuiz(id : String)
}