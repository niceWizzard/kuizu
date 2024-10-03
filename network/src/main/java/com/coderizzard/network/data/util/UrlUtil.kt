package com.coderizzard.network.data.util

internal fun extractQuizId(url : String) : String? {
    val regex = """/quiz/([a-zA-Z0-9]+)""".toRegex()
    return regex.find(url)?.groups?.get(1)?.value
}