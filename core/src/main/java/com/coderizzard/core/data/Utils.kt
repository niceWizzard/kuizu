package com.coderizzard.core.data

import android.text.Html
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration

fun stripHtmlTags(s : String ): String {
    return s.replace(Regex("</?p>"), "").trim()
}

fun String.toAnnotatedString(): AnnotatedString {
    val spanned: Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)

    return buildAnnotatedString {
        append(spanned.toString().trim())

        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)

            when (span) {
                is StyleSpan -> when (span.style) {
                    android.graphics.Typeface.BOLD -> {
                        addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                    }
                    android.graphics.Typeface.ITALIC -> {
                        addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                    }
                    android.graphics.Typeface.BOLD_ITALIC -> addStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        ), start, end
                    )
                }
                is UnderlineSpan -> {
                    addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                }
                is SuperscriptSpan -> {
                    addStyle(SpanStyle(baselineShift = BaselineShift.Superscript), start, end)
                }
                is SubscriptSpan -> {
                    addStyle(SpanStyle(baselineShift = BaselineShift.Subscript), start, end)
                }
            }
        }
    }
}