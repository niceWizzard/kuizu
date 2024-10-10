package com.coderizzard.database.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coderizzard.quiz.data.model.Question
import com.coderizzard.quiz.data.model.Quiz
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class QuizEntity (
    @PrimaryKey
    val id : String = UUID.randomUUID().toString(),
    @ColumnInfo("name")
    val name : String,
    @ColumnInfo("author")
    val author : String,
    @ColumnInfo("image_link")
    val imageLink : String,
    @ColumnInfo("created_at")
    val createdAt : LocalDateTime,
) {
    fun toQuiz(questions : List<Question>) : Quiz {
        return Quiz(
            id = this.id,
            author = this.author,
            name = this.name,
            createdAt = this.createdAt,
            questions = questions
        )
    }
}