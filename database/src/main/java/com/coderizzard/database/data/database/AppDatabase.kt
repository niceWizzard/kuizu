package com.coderizzard.database.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
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
abstract class AppDatabase : RoomDatabase() {
    abstract val quizDao : QuizDao
}