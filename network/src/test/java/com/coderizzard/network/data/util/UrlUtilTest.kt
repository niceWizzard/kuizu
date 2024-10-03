package com.coderizzard.network.data.util

import org.junit.Assert
import org.junit.Test

internal class UrlUtilTest {
    @Test
    fun testValidUrl() {
        val url1 = "https://quizizz.com/join/quiz/abc123/?start=true"

        Assert.assertEquals("abc123", extractQuizId(url1))

        val url2 = "https://quizizz.com/admin/quiz/xyz789/?start=true"
        Assert.assertEquals("xyz789", extractQuizId(url2))
    }

    @Test
    fun testInvalidUrl() {
        val url = "https://quizizz.com/admin/quizx/xyz789/?start=true"
        Assert.assertNull(extractQuizId(url))

        Assert.assertNull(extractQuizId(""))
        Assert.assertNull(extractQuizId("alksdfjklasdfj"))
    }



}