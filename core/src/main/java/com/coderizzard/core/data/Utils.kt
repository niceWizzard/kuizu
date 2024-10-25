package com.coderizzard.core.data

fun stripHtmlTags(s : String ): String {
    return s.replace(Regex("</?p>"), "").trim()
}