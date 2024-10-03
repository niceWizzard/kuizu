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

    @Test
    fun testValidForGetQuizId() {
        val url1 = "https://quizizz.com/join/quiz/abc123/?start=true"
        Assert.assertEquals("abc123", getQuizId(url1))

        val id = "asd123fafsdaasdf"
        Assert.assertEquals(id, getQuizId(id))

        val id2 = "lkajikljewAdfsA"
        Assert.assertEquals(id2, getQuizId(id2))
    }

    @Test
    fun testInvalidForGetQuizId() {
        val url = "https://quizizz.com/admin/quizx/xyz789/?start=true"
        Assert.assertNull(getQuizId(url))

        Assert.assertNull(getQuizId(""))
        Assert.assertNull(getQuizId("alksdfjklasdfj!"))
        Assert.assertNull(getQuizId("alksasfddfjklasdfj23@2"))
    }

}