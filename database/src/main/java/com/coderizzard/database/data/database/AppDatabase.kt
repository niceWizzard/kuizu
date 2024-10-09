package com.coderizzard.database.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coderizzard.database.data.DbConverter
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MultipleChoiceQuestionEntity

@Database(
    entities = [
        QuizEntity::class,
        MultipleChoiceQuestionEntity::class,
        IdentificationQuestionEntity::class,
    ],
    version = 1
)
@TypeConverters(
    DbConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract val quizDao : QuizDao
}