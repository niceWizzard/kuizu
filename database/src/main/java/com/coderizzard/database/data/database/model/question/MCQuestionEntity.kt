package com.coderizzard.database.data.database.model.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.question.MCOption
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.database.data.database.model.QuizEntity
import java.util.UUID

@Entity(
    tableName = "mc_question",
    foreignKeys = [
        ForeignKey(
            parentColumns = [QuizEntity.ID],
            onDelete = ForeignKey.CASCADE,
            entity = QuizEntity::class,
            childColumns = [QuestionEntity.QUIZ_ID]
        )
    ]
)
data class MCQuestionEntity(
    @PrimaryKey
    @ColumnInfo(name = QuestionEntity.ID)
    override val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = QuestionEntity.REMOTE_ID)
    override val remoteId: String,
    @ColumnInfo(name = QuestionEntity.TEXT)
    override val text: String,
    @ColumnInfo(name = QuestionEntity.POINT)
    override val point: Int,
    @ColumnInfo(name = QuestionEntity.QUIZ_ID)
    override val quizId: String,
    @ColumnInfo(name = ANSWER)
    val answer: List<Int>,
    @ColumnInfo(name = QuestionEntity.IMAGE_LINK)
    override val imageLink: String,
    @ColumnInfo(name = QuestionEntity.LOCAL_IMAGE_PATH)
    override val localImagePath: String
) : QuestionEntity {
    companion object {
        const val ANSWER = "answer"
    }
    fun toMCQuestion(options : List<MCOption>) : MCQuestion {
        return MCQuestion(
            id = this.id,
            remoteId = this.remoteId,
            text = this.text,
            point = this.point,
            answer = this.answer,
            options = options,
            quizId = this.quizId,
            imageLink = this.imageLink,
            localImagePath = this.localImagePath

        )
    }
}