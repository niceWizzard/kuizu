package com.coderizzard.database.data.database.model.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.question.IdentificationQuestion
import java.util.UUID

@Entity
data class IdentificationQuestionEntity(
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
    val answer: String,
    @ColumnInfo(name = QuestionEntity.IMAGE_LINK)
    override val imageLink: String,
    @ColumnInfo(name = QuestionEntity.LOCAL_IMAGE_PATH)
    override val localImagePath: String
) : QuestionEntity {
    companion object {
        const val ANSWER = "answer"
    }
    fun toIdentificationQuestion() : IdentificationQuestion {
        return IdentificationQuestion(
            id = this.id,
            text = this.text,
            point = this.point,
            answer = this.answer,
            quizId = this.quizId,
            remoteId = this.remoteId,
            imageLink = this.imageLink,
            localImagePath = this.localImagePath
        )
    }
}