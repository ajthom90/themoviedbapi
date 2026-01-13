package uk.co.conoregan.themoviedbapi.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

/**
 * Comprehensive tests for ApiUrl utility class.
 */
class ApiUrlComprehensiveTest {

    @Test
    fun testEmptyPath() {
        val apiUrl = ApiUrl()
        assertEquals("", apiUrl.getPath())
        assertTrue(apiUrl.getParameters().isEmpty())
    }

    @Test
    fun testSingleSegment() {
        val apiUrl = ApiUrl("movie")
        assertEquals("movie", apiUrl.getPath())
    }

    @Test
    fun testMultipleStringSegments() {
        val apiUrl = ApiUrl("movie", "popular")
        assertEquals("movie/popular", apiUrl.getPath())
    }

    @Test
    fun testMixedTypeSegments() {
        val apiUrl = ApiUrl("movie", 550, "credits")
        assertEquals("movie/550/credits", apiUrl.getPath())
    }

    @Test
    fun testIntegerSegments() {
        val apiUrl = ApiUrl("person", 287, "images")
        assertEquals("person/287/images", apiUrl.getPath())
    }

    @Test
    fun testAddStringParam() {
        val apiUrl = ApiUrl("search", "movie")
        apiUrl.addParam("query", "Fight Club")

        val params = apiUrl.getParameters()
        assertEquals("Fight Club", params["query"])
    }

    @Test
    fun testAddIntParam() {
        val apiUrl = ApiUrl("movie", "popular")
        apiUrl.addParam("page", 2)

        val params = apiUrl.getParameters()
        assertEquals("2", params["page"])
    }

    @Test
    fun testAddBooleanParam() {
        val apiUrl = ApiUrl("discover", "movie")
        apiUrl.addParam("include_adult", true)
        apiUrl.addParam("include_video", false)

        val params = apiUrl.getParameters()
        assertEquals("true", params["include_adult"])
        assertEquals("false", params["include_video"])
    }

    @Test
    fun testAddNullStringParam() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addParam("test", null as String?)

        val params = apiUrl.getParameters()
        assertFalse(params.containsKey("test"))
    }

    @Test
    fun testAddNullIntParam() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addParam("page", null as Int?)

        val params = apiUrl.getParameters()
        assertFalse(params.containsKey("page"))
    }

    @Test
    fun testAddNullBooleanParam() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addParam("include_adult", null as Boolean?)

        val params = apiUrl.getParameters()
        assertFalse(params.containsKey("include_adult"))
    }

    @Test
    fun testAddLanguageValid() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addLanguage("en-US")

        val params = apiUrl.getParameters()
        assertEquals("en-US", params["language"])
    }

    @Test
    fun testAddLanguageNull() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addLanguage(null)

        val params = apiUrl.getParameters()
        assertFalse(params.containsKey("language"))
    }

    @Test
    fun testAddPageValid() {
        val apiUrl = ApiUrl("movie", "popular")
        apiUrl.addPage(3)

        val params = apiUrl.getParameters()
        assertEquals("3", params["page"])
    }

    @Test
    fun testAddPageNull() {
        val apiUrl = ApiUrl("movie", "popular")
        apiUrl.addPage(null)

        val params = apiUrl.getParameters()
        assertFalse(params.containsKey("page"))
    }

    @Test
    fun testAddAppendToResponseSingle() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addAppendToResponse("credits")

        val params = apiUrl.getParameters()
        assertEquals("credits", params["append_to_response"])
    }

    @Test
    fun testAddAppendToResponseMultiple() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addAppendToResponse("credits", "videos", "images")

        val params = apiUrl.getParameters()
        assertEquals("credits,videos,images", params["append_to_response"])
    }

    @Test
    fun testAddAppendToResponseEmpty() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addAppendToResponse()

        val params = apiUrl.getParameters()
        assertFalse(params.containsKey("append_to_response"))
    }

    @Test
    fun testAddAppendToResponseVararg() {
        val apiUrl = ApiUrl("person", 287)
        val responses = arrayOf("combined_credits", "external_ids", "images")
        apiUrl.addAppendToResponse(*responses)

        val params = apiUrl.getParameters()
        assertEquals("combined_credits,external_ids,images", params["append_to_response"])
    }

    @Test
    fun testMultipleParameterTypes() {
        val apiUrl = ApiUrl("search", "movie")
        apiUrl.addParam("query", "Inception")
        apiUrl.addLanguage("en-US")
        apiUrl.addPage(1)
        apiUrl.addParam("include_adult", false)
        apiUrl.addParam("year", "2010")

        val params = apiUrl.getParameters()
        assertEquals(5, params.size)
        assertEquals("Inception", params["query"])
        assertEquals("en-US", params["language"])
        assertEquals("1", params["page"])
        assertEquals("false", params["include_adult"])
        assertEquals("2010", params["year"])
    }

    @Test
    fun testOverwriteParameter() {
        val apiUrl = ApiUrl("movie", "popular")
        apiUrl.addParam("page", 1)
        apiUrl.addParam("page", 2)

        val params = apiUrl.getParameters()
        assertEquals("2", params["page"])
    }

    @Test
    fun testComplexPath() {
        val apiUrl = ApiUrl("movie", 550, "reviews", "page", 2)
        assertEquals("movie/550/reviews/page/2", apiUrl.getPath())
    }

    @Test
    fun testParameterImmutability() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addParam("test", "value")

        val params1 = apiUrl.getParameters()
        val params2 = apiUrl.getParameters()

        assertEquals(params1, params2)
    }

    @Test
    fun testZeroAsSegment() {
        val apiUrl = ApiUrl("movie", 0)
        assertEquals("movie/0", apiUrl.getPath())
    }

    @Test
    fun testNegativeNumberAsSegment() {
        val apiUrl = ApiUrl("movie", -1)
        assertEquals("movie/-1", apiUrl.getPath())
    }

    @Test
    fun testVeryLongPath() {
        val apiUrl = ApiUrl("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")
        assertEquals("a/b/c/d/e/f/g/h/i/j", apiUrl.getPath())
    }

    @Test
    fun testSpecialCharactersInParams() {
        val apiUrl = ApiUrl("search", "movie")
        apiUrl.addParam("query", "Fight Club & Friends")

        val params = apiUrl.getParameters()
        assertEquals("Fight Club & Friends", params["query"])
    }

    @Test
    fun testEmptyStringParameter() {
        val apiUrl = ApiUrl("test")
        apiUrl.addParam("empty", "")

        val params = apiUrl.getParameters()
        assertEquals("", params["empty"])
    }

    @Test
    fun testMultipleLanguages() {
        val apiUrl = ApiUrl("movie", 550)
        apiUrl.addLanguage("en-US")
        apiUrl.addLanguage("fr-FR") // Should overwrite

        val params = apiUrl.getParameters()
        assertEquals("fr-FR", params["language"])
    }

    @Test
    fun testChainedCalls() {
        val apiUrl = ApiUrl("movie", "popular")
        apiUrl.addLanguage("en-US")
        apiUrl.addPage(1)
        apiUrl.addParam("region", "US")

        // Method chaining would work if we return 'this' from methods
        val params = apiUrl.getParameters()
        assertEquals(3, params.size)
    }
}
