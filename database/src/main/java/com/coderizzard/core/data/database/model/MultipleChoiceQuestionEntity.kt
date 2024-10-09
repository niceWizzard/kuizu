package com.coderizzard.core.data.database.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class MultipleChoiceQuestionEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo("text")
    override val text: String,
    @ColumnInfo("point")
    override val point: Int,
    @ColumnInfo("quizId")
    override val quizId: String,
): QuestionEntity