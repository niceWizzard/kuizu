package com.coderizzard.database.data.database.model.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.question.IdentificationQuestion
import java.util.UUID

@Entity
data class IdentificationQuestionEntity(
    @PrimaryKey
    override val id: String = UUID.randomUUID().toString(),
    @ColumnInfo("remote_id")
    override val remoteId : String,
    @ColumnInfo("text")
    override val text: String,
    @ColumnInfo("point")
    override val point: Int,
    @ColumnInfo("quiz_id")
    override val quizId: String,
    @ColumnInfo("answer")
    val answer : String,
    @ColumnInfo("image_link")
    override val imageLink: String,
    @ColumnInfo("local_imagePath")
    override val localImagePath: String
) : QuestionEntity {
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