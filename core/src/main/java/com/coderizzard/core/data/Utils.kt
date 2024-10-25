package com.coderizzard.core.data

import android.text.Html
import android.text.Spanned
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

fun stripHtmlTags(s : String ): String {
    return s.replace(Regex("</?p>"), "").trim()
}

fun String.toAnnotatedString(): AnnotatedString {
    val spanned: Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    return buildAnnotatedString {
        append(spanned.toString())
        // You can add further parsing for specific tags here if needed.
        // For example, if you want to handle <b> or <i>, you can loop through the spans.
    }
}