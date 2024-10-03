package com.coderizzard.network.data.util

internal fun extractQuizId(url : String) : String? {
    val regex = """/quiz/([a-zA-Z0-9]+)""".toRegex()
    return regex.find(url)?.groups?.get(1)?.value
}

internal fun getQuizId(urlOrId : String) : String? {
    val urlRegex = """^(https?:\/\/(www\.)?quizizz\.com.*|quizizz\.com.+)${'$'}""".toRegex()
    if(urlRegex.matches(urlOrId)) {
        return extractQuizId(urlOrId)
    }
    val idRegexValidator = """^[a-zA-Z0-9]+$""".toRegex()
    if(idRegexValidator.matches(urlOrId)) {
        return urlOrId
    }
    return null
}