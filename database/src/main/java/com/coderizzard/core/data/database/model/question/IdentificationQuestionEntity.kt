package com.coderizzard.core.data.database.model.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IdentificationQuestionEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo("text")
    override val text: String,
    @ColumnInfo("point")
    override val point: Int,
    @ColumnInfo("quiz_id")
    override val quizId: String
) : QuestionEntity {
}