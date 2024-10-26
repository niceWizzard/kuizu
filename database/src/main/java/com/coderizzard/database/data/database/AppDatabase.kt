package com.coderizzard.database.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coderizzard.database.data.DbConverter
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCOptionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity

@Database(
    entities = [
        QuizEntity::class,
        MCQuestionEntity::class,
        IdentificationQuestionEntity::class,
        MCOptionEntity::class,
    ],
    version = 1
)
@TypeConverters(
    DbConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract val quizDao : QuizDao
    abstract val questionDao : QuestionDao
}