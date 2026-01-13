package uk.co.conoregan.themoviedbapi.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests for ApiUrl utility class.
 */
class ApiUrlTest {

    @Test
    fun testBasicPath() {
        val apiUrl = ApiUrl("movie", 550)
        assertEquals("movie/550", apiUrl.getPath())
    }

    @Test
    fun testPathWithMultipleSegments() {
        val apiUrl = ApiUrl("movie", 550, "credits")
        assertEquals("movie/550/credits", apiUrl.getPath())
    }

    @Test
    fun testAddLanguageParameter() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addLanguage("en-US")

        val params = apiUrl.getParameters()
        assertEquals("en-US", params["language"])
    }

    @Test
    fun testAddPageParameter() {
        val apiUrl = ApiUrl("movie", "popular")
        apiUrl.addPage(2)

        val params = apiUrl.getParameters()
        assertEquals("2", params["page"])
    }

    @Test
    fun testAddNullParameter() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addParam("test", null as String?)

        val params = apiUrl.getParameters()
        assertTrue(!params.containsKey("test"), "Null parameters should not be added")
    }

    @Test
    fun testMultipleParameters() {
        val apiUrl = ApiUrl("search", "movie")
        apiUrl.addParam("query", "Fight Club")
        apiUrl.addLanguage("en-US")
        apiUrl.addPage(1)
        apiUrl.addParam("include_adult", false)

        val params = apiUrl.getParameters()
        assertEquals("Fight Club", params["query"])
        assertEquals("en-US", params["language"])
        assertEquals("1", params["page"])
        assertEquals("false", params["include_adult"])
    }

    @Test
    fun testAppendToResponse() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addAppendToResponse("credits", "videos", "images")

        val params = apiUrl.getParameters()
        assertEquals("credits,videos,images", params["append_to_response"])
    }

    @Test
    fun testEmptyAppendToResponse() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addAppendToResponse()

        val params = apiUrl.getParameters()
        assertTrue(!params.containsKey("append_to_response"),
                   "Empty append_to_response should not be added")
    }
}
