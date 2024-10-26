package com.coderizzard.database.data.database.model.question


sealed interface QuestionEntity {
    val id : String
    val text : String
    val point : Int
    val quizId : String
    val remoteId : String
    val imageLink : String
    val localImagePath : String

    companion object {
        const val ID = "id"
        const val TEXT = "text"
        const val POINT = "point"
        const val QUIZ_ID = "quiz_id"
        const val REMOTE_ID = "remote_id"
        const val IMAGE_LINK = "image_link"
        const val LOCAL_IMAGE_PATH = "local_image_path"
    }
}