package com.coderizzard.network.data

import com.coderizzard.network.data.model.ExtractedIdentificationQuestion
import com.coderizzard.network.data.model.ExtractedMultipleChoiceQuestion
import com.coderizzard.network.data.model.ExtractedQuestion
import com.coderizzard.network.data.model.ExtractedQuiz
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ExtractedQuizJsonDeserializer : JsonDeserializer<ExtractedQuiz> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ExtractedQuiz {
        val jsonObject = json.asJsonObject

        if(!jsonObject.getAsJsonPrimitive("success").asBoolean) {
            throw Exception("Success false")
        }

        val quiz = jsonObject.getAsJsonObject("data").getAsJsonObject("quiz")

        val author = quiz.get("createdBy").asJsonObject.get("local").asJsonObject.get("username").asString

        val info = quiz.get("info").asJsonObject

        val name = info.get("name").asString

        val imageLink = info.get("image").asString

        val createdAtString = info.get("createdAt").asString

        val questions : List<ExtractedQuestion> = emptyList()

        return ExtractedQuiz(
            name = name,
            author = author,
            imageLink = imageLink,
            questionList = questions,
            createdAt = LocalDateTime.parse(createdAtString)
        )


    }
}