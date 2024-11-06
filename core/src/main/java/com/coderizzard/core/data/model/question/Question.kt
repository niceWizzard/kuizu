package com.coderizzard.core.data.model.question

import com.coderizzard.core.data.stripHtmlTags

sealed interface Question {
    val id : String
    val text : String
    val point : Int
    val quizId : String
    val remoteId : String
    val imageLink: String
    val localImagePath: String

    fun plainText(): String {
        return stripHtmlTags(text)
    }

    fun getTypeName(): String {
        return when(this) {
            is IdentificationQuestion -> "Identification"
            is MCQuestion -> "Multiple Choice"
            is UnsupportedQuestion -> "Unsupported"
        }
    }

}