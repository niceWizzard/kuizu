package com.coderizzard.database.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
)