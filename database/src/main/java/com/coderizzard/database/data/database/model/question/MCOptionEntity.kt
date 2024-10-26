package com.coderizzard.database.data.database.model.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    val id: String,
    @ColumnInfo(name = QUESTION_ID)
    val questionId: String,
    @ColumnInfo(name = TEXT)
    val text: String
) {
    companion object {
        const val ID = "id"
        const val QUESTION_ID = "question_id"
        const val TEXT = "text"
    }
}