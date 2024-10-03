package com.coderizzard.network.data.util

import org.junit.Assert
import org.junit.Test

internal class UrlUtilTest {
    @Test
    fun testValidUrl() {
        val url1 = "https://quizizz.com/join/quiz/abc123/?start=true"

        Assert.assertEquals("abc123", extractQuizIdFromUrl(url1))

        val url2 = "https://quizizz.com/admin/quiz/xyz789/?start=true"
        Assert.assertEquals("xyz789", extractQuizIdFromUrl(url2))
    }

    @Test
    fun testInvalidUrl() {
        val url = "https://quizizz.com/admin/quizx/xyz789/?start=true"
        Assert.assertNull(extractQuizIdFromUrl(url))

        Assert.assertNull(extractQuizIdFromUrl(""))
        Assert.assertNull(extractQuizIdFromUrl("alksdfjklasdfj"))
    }

    @Test
    fun testValidForGetQuizId() {
        val url1 = "https://quizizz.com/join/quiz/abc123/?start=true"
        Assert.assertEquals("abc123", resolveQuizId(url1))

        val id = "asd123fafsdaasdf"
        Assert.assertEquals(id, resolveQuizId(id))

        val id2 = "lkajikljewAdfsA"
        Assert.assertEquals(id2, resolveQuizId(id2))
    }

    @Test
    fun testInvalidForGetQuizId() {
        val url = "https://quizizz.com/admin/quizx/xyz789/?start=true"
        Assert.assertNull(resolveQuizId(url))

        Assert.assertNull(resolveQuizId(""))
        Assert.assertNull(resolveQuizId("alksdfjklasdfj!"))
        Assert.assertNull(resolveQuizId("alksasfddfjklasdfj23@2"))
    }

}