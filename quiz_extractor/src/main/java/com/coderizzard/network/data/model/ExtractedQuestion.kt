package com.coderizzard.network.data.model


sealed interface ExtractedQuestion {
    val text : String
    val remoteId : String
}

data class ExtractedMultipleChoiceQuestion(
    override val text: String,
    override val remoteId: String,
    val answer : List<Int>,
    val options : List<String>
) : ExtractedQuestion

data class ExtractedIdentificationQuestion(
    override val text: String,
    override val remoteId: String,
    val answer : String,
) : ExtractedQuestion