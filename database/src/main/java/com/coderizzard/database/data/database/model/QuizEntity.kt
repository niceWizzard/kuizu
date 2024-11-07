package com.coderizzard.database.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.Question
import java.time.LocalDateTime
import java.util.UUID

@Entity(
    tableName = "quiz"
)
data class QuizEntity(
    @PrimaryKey()
    @ColumnInfo(ID)
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = AUTHOR)
    val author: String,
    @ColumnInfo(name = IMAGE_LINK)
    val imageLink: String,
    @ColumnInfo(name = LOCAL_IMAGE_PATH)
    val localImagePath: String,
    @ColumnInfo(name = CREATED_AT)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = REMOTE_ID)
    val remoteId: String,
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val AUTHOR = "author"
        const val IMAGE_LINK = "image_link"
        const val LOCAL_IMAGE_PATH = "local_image_path"
        const val CREATED_AT = "created_at"
        const val REMOTE_ID = "remote_id"
    }
    fun toQuiz(questions : List<Question>) : Quiz {
        return Quiz(
            id = this.id,
            author = this.author,
            name = this.name,
            createdAt = this.createdAt,
            allQuestions = questions,
            remoteId = this.remoteId,
            imageLink = this.imageLink,
            localImagePath = this.localImagePath
        )
    }
}

fun Quiz.toEntity()  : QuizEntity {
    return QuizEntity(
        id = this.id,
        name = this.name,
        author = this.author,
        imageLink = this.imageLink,
        localImagePath = this.localImagePath,
        createdAt = this.createdAt,
        remoteId = this.remoteId,
    )
}