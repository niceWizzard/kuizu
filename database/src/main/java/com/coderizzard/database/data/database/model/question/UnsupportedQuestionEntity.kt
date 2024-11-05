package com.coderizzard.database.data.database.model.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.question.UnsupportedQuestion
import java.util.UUID

@Entity(
    tableName = "unsupported_question"
)
data class UnsupportedQuestionEntity(
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
    @ColumnInfo(name = QuestionEntity.IMAGE_LINK)
    override val imageLink: String,
    @ColumnInfo(name = QuestionEntity.LOCAL_IMAGE_PATH)
    override val localImagePath: String,
    @ColumnInfo(name = TYPE)
    val type : String,
) : QuestionEntity {
    companion object  {
        const val TYPE = "type"
    }

    fun toUnsupportedQuestion() : UnsupportedQuestion {
        return UnsupportedQuestion(
            id = this.id,
            imageLink = this.imageLink,
            text = this.text,
            localImagePath = this.localImagePath,
            remoteId = this.remoteId,
            point = this.point,
            quizId = this.quizId,
            type = this.type,
        )
    }
}

fun UnsupportedQuestion.toEntity() : UnsupportedQuestionEntity {
    return UnsupportedQuestionEntity(
        imageLink = this.imageLink,
        text = this.text,
        localImagePath = this.localImagePath,
        remoteId = this.remoteId,
        point = this.point,
        quizId = this.quizId,
        type = this.type,
    )
}