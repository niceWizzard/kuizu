package com.coderizzard.database.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coderizzard.database.data.DbConverter
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.SessionResultEntity
import com.coderizzard.database.data.database.model.session.answers.IdentificationAnswerEntity
import com.coderizzard.database.data.database.model.session.answers.MCQuestionAnswerEntity

@Database(
    entities = [
        QuizEntity::class,
        MCQuestionEntity::class,
        IdentificationQuestionEntity::class,
        IdentificationAnswerEntity::class,
        MCQuestionAnswerEntity::class,
        QuizSessionEntity::class,
        SessionResultEntity::class
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