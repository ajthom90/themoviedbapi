package uk.co.conoregan.themoviedbapi.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

/**
 * Tests for exception classes.
 */
class TmdbExceptionTest {

    @Test
    fun testTmdbExceptionCreation() {
        val exception = TmdbException("Test message")
        assertEquals("Test message", exception.message)
        assertEquals(null, exception.cause)
    }

    @Test
    fun testTmdbExceptionWithCause() {
        val cause = RuntimeException("Cause exception")
        val exception = TmdbException("Test message", cause)
        assertEquals("Test message", exception.message)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun testTmdbResponseException() {
        val exception = TmdbResponseException(
            statusCode = 404,
            statusMessage = "Not Found",
            success = false
        )
        assertEquals(404, exception.statusCode)
        assertEquals("Not Found", exception.statusMessage)
        assertFalse(exception.success)
        assertTrue(exception.message?.contains("404") == true)
        assertTrue(exception.message?.contains("Not Found") == true)
    }

    @Test
    fun testTmdbNetworkException() {
        val exception = TmdbNetworkException("Network error")
        assertEquals("Network error", exception.message)
        assertEquals(null, exception.cause)
    }

    @Test
    fun testTmdbNetworkExceptionWithCause() {
        val cause = RuntimeException("Connection timeout")
        val exception = TmdbNetworkException("Network error", cause)
        assertEquals("Network error", exception.message)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun testTmdbSerializationException() {
        val exception = TmdbSerializationException("JSON parse error")
        assertEquals("JSON parse error", exception.message)
        assertEquals(null, exception.cause)
    }

    @Test
    fun testTmdbSerializationExceptionWithCause() {
        val cause = RuntimeException("Invalid JSON")
        val exception = TmdbSerializationException("JSON parse error", cause)
        assertEquals("JSON parse error", exception.message)
        assertEquals(cause, exception.cause)
    }

    @Test
    fun testExceptionInheritance() {
        val responseException = TmdbResponseException(500, "Server Error")
        val networkException = TmdbNetworkException("Network down")
        val serializationException = TmdbSerializationException("Parse failed")

        assertTrue(responseException is TmdbException)
        assertTrue(networkException is TmdbException)
        assertTrue(serializationException is TmdbException)
    }

    @Test
    fun testExceptionMessageFormats() {
        val exception1 = TmdbResponseException(401, "Unauthorized", false)
        assertTrue(exception1.message?.contains("401") == true)

        val exception2 = TmdbResponseException(429, "Too Many Requests", false)
        assertTrue(exception2.message?.contains("429") == true)
        assertTrue(exception2.message?.contains("Too Many Requests") == true)
    }
}

/**
 * Tests for RequestMethod enum.
 */
class RequestMethodTest {

    @Test
    fun testRequestMethodValues() {
        val values = RequestMethod.values()
        assertEquals(3, values.size)
        assertTrue(values.contains(RequestMethod.GET))
        assertTrue(values.contains(RequestMethod.POST))
        assertTrue(values.contains(RequestMethod.DELETE))
    }

    @Test
    fun testRequestMethodValueOf() {
        assertEquals(RequestMethod.GET, RequestMethod.valueOf("GET"))
        assertEquals(RequestMethod.POST, RequestMethod.valueOf("POST"))
        assertEquals(RequestMethod.DELETE, RequestMethod.valueOf("DELETE"))
    }

    @Test
    fun testRequestMethodNames() {
        assertEquals("GET", RequestMethod.GET.name)
        assertEquals("POST", RequestMethod.POST.name)
        assertEquals("DELETE", RequestMethod.DELETE.name)
    }
}
