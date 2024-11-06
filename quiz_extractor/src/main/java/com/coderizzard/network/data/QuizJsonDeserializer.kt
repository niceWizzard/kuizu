package com.coderizzard.network.data

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCOption
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.question.Question
import com.coderizzard.core.data.model.question.UnsupportedQuestion
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class QuizJsonDeserializer : JsonDeserializer<Quiz> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Quiz {
        return deserializeJson(json)
    }

    internal fun deserializeJson(json : JsonElement): Quiz {
        val jsonObject = json.asJsonObject

        if(!jsonObject.getAsJsonPrimitive("success").asBoolean) {
            throw Exception("Success false")
        }

        val quiz = jsonObject.getAsJsonObject("data").getAsJsonObject("quiz")

        val quizId = quiz.get("_id").asString

        val author = quiz.get("createdBy").asJsonObject.get("local").asJsonObject.get("username").asString

        val info = quiz.get("info").asJsonObject

        val name = info.get("name").asString

        val imageLink = if(info.has("image"))
            info.get("image").asString
        else
            ""

        val createdAtString = info.get("createdAt").asString


        val questionJsonArray = info.get("questions").asJsonArray
        val questions : List<Question> = questionJsonArray.map {
            it.asJsonObject
        }.map { questionJson ->
            val type = questionJson.get("type").asString
            val structure = questionJson.get("structure").asJsonObject
            val query = structure.get("query").asJsonObject
            val text = query.get("text").asString
            val questionId = questionJson.get("_id").asString
            val options = structure.get("options").asJsonArray.map { it.asJsonObject }
            val media  = query.get("media").asJsonArray
            val image = if(media.size() > 0)
                media.filter { it.asJsonObject.get("type").asString.lowercase() == "image" }[0].asJsonObject.get("url").asString
            else ""
            return@map when(type.lowercase() ) {
                "mcq","msq" -> {
                    val optionsList = options.map {
                        val optionText = it.get("text").asString
                        val optionId = it.get("id").asString
                        MCOption(
                            id = "",
                            questionId = "",
                            remoteId = optionId,
                            text = optionText,
                        )
                    }
                    MCQuestion(
                        id = "",
                        remoteId = questionId,
                        text = text,
                        options = optionsList,
                        point = 1,
                        quizId = "",
                        imageLink = image,
                        answer = structure.get("answer").let { answer ->
                            if(answer.isJsonPrimitive) {
                                return@let listOf(
                                    optionsList[answer.asJsonPrimitive.asInt].remoteId
                                )
                            }else {
                                return@let answer.asJsonArray.map { optionsList[it.asJsonPrimitive.asInt].remoteId }
                            }
                        },
                    )
                }
                "blank" -> {
                    IdentificationQuestion(
                        id = "",
                        remoteId = questionId,
                        quizId = "",
                        text = text,
                        imageLink = image,
                        answer = options[0].get("text").asString,
                        point = 1,
                    )
                }
                else -> {
                    UnsupportedQuestion(
                        id = "",
                        remoteId = questionId,
                        point = 1,
                        quizId = "",
                        type = type,
                        text = text,
                        imageLink = image,
                        localImagePath = "",
                    )
                }
            }
        }



        return Quiz(
            id = "",
            remoteId = quizId,
            name = name,
            author = author,
            imageLink = imageLink,
            allQuestions = questions,
            createdAt = LocalDateTime.ofInstant(
                Instant.parse(createdAtString),
                ZoneId.systemDefault()
            )
        )
    }
}