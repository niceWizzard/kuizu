package com.coderizzard.database.data.database.model.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.question.MCOption
import java.util.UUID

@Entity(
    tableName = "mc_option",
    foreignKeys = [
        ForeignKey(
            parentColumns = [QuestionEntity.ID],
            entity = MCQuestionEntity::class,
            childColumns = [MCOptionEntity.QUESTION_ID],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class MCOptionEntity(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = QUESTION_ID)
    val questionId: String,
    @ColumnInfo(name = TEXT)
    val text: String,
    @ColumnInfo(name = REMOTE_ID)
    val remoteId : String
) {
    companion object {
        const val ID = "id"
        const val QUESTION_ID = "question_id"
        const val TEXT = "text"
        const val REMOTE_ID = "remote_id"
    }

    fun toMCOption() : MCOption {
        return MCOption(
            id = this.id,
            text = this.text,
            questionId = this.questionId,
            remoteId = this.remoteId,
        )
    }
}

fun MCOption.toEntity() : MCOptionEntity {
    return MCOptionEntity(
        id = this.id,
        text = this.text,
        questionId = this.questionId,
        remoteId = this.remoteId,
    )
}