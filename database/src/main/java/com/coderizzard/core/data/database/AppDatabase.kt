package com.coderizzard.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coderizzard.core.data.database.model.QuizEntity
import com.coderizzard.core.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.core.data.database.model.question.MultipleChoiceQuestionEntity

@Database(
    entities = [
        QuizEntity::class,
        MultipleChoiceQuestionEntity::class,
        IdentificationQuestionEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
}