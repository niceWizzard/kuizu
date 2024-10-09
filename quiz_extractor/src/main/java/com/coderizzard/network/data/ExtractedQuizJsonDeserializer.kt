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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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


        val questions : List<ExtractedQuestion> = info.get("questions").asJsonArray.map {
            it.asJsonObject
        }.filter {
            val type = it.get("type").asString.lowercase()
            return@filter type == "mcq" || type == "blank"
        }.map { questionJson ->
            val type = questionJson.get("type").asString
            val structure = questionJson.get("structure").asJsonObject
            val text = structure.get("query").asJsonObject.get("text").asString
            val options = structure.get("options").asJsonArray.map { it.asJsonObject }
            return@map when(type.lowercase() ) {
                "mcq" -> {
                    ExtractedMultipleChoiceQuestion(
                        text = text,
                        options = options.map {
                            it.get("text").asString
                        },
                        answer = structure.get("answer").let {
                            if(it.isJsonPrimitive) {
                                return@let listOf(it.asJsonPrimitive.asInt)
                            }else {
                                return@let it.asJsonArray.map { it.asJsonPrimitive.asInt }
                            }
                        },
                        remoteId = ""
                    )
                }
                "blank" -> {
                    ExtractedIdentificationQuestion(
                        text = text,
                        answer = options[0].get("text").asString,
                        remoteId = ""
                    )
                }
                else -> throw Exception("Invalid question type received.")
            }
        }



        return ExtractedQuiz(
            name = name,
            author = author,
            imageLink = imageLink,
            questionList = questions,
            createdAt = LocalDateTime.ofInstant(
                Instant.parse(createdAtString),
                ZoneId.systemDefault()
            )
        )


    }
}