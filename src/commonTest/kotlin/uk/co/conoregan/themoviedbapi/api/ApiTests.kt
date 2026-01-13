package uk.co.conoregan.themoviedbapi.api

import uk.co.conoregan.themoviedbapi.api.TmdbApi
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Comprehensive tests for TmdbSearch API.
 */
class TmdbSearchTest {

    @Test
    fun testSearchApiInitialization() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api.search)
        api.close()
    }

    @Test
    fun testSearchApiAccessible() {
        val api = TmdbApi(apiKey = "test-api-key")
        val search = api.search
        assertNotNull(search)
        api.close()
    }

    @Test
    fun testMultipleSearchInstancesAreSame() {
        val api = TmdbApi(apiKey = "test-api-key")
        val search1 = api.search
        val search2 = api.search
        assertEquals(search1, search2, "Search API should use lazy initialization")
        api.close()
    }
}

/**
 * Comprehensive tests for TmdbTrending API.
 */
class TmdbTrendingTest {

    @Test
    fun testTrendingApiInitialization() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api.trending)
        api.close()
    }

    @Test
    fun testTimeWindowEnum() {
        val day = TmdbTrending.TimeWindow.DAY
        val week = TmdbTrending.TimeWindow.WEEK

        assertEquals("day", day.value)
        assertEquals("week", week.value)
    }

    @Test
    fun testAllTimeWindowValues() {
        val values = TmdbTrending.TimeWindow.values()
        assertEquals(2, values.size)
        assertEquals(TmdbTrending.TimeWindow.DAY, values[0])
        assertEquals(TmdbTrending.TimeWindow.WEEK, values[1])
    }
}

/**
 * Comprehensive tests for TmdbConfiguration API.
 */
class TmdbConfigurationTest {

    @Test
    fun testConfigurationApiInitialization() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api.configuration)
        api.close()
    }

    @Test
    fun testConfigurationApiAccessible() {
        val api = TmdbApi(apiKey = "test-api-key")
        val config = api.configuration
        assertNotNull(config)
        api.close()
    }
}

/**
 * Comprehensive tests for TmdbGenres API.
 */
class TmdbGenresTest {

    @Test
    fun testGenresApiInitialization() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api.genres)
        api.close()
    }

    @Test
    fun testGenresApiAccessible() {
        val api = TmdbApi(apiKey = "test-api-key")
        val genres = api.genres
        assertNotNull(genres)
        api.close()
    }
}

/**
 * Comprehensive tests for TmdbCollections API.
 */
class TmdbCollectionsTest {

    @Test
    fun testCollectionsApiInitialization() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api.collections)
        api.close()
    }

    @Test
    fun testCollectionsApiAccessible() {
        val api = TmdbApi(apiKey = "test-api-key")
        val collections = api.collections
        assertNotNull(collections)
        api.close()
    }
}

/**
 * Comprehensive tests for TmdbPeople API.
 */
class TmdbPeopleTest {

    @Test
    fun testPeopleApiInitialization() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api.people)
        api.close()
    }

    @Test
    fun testPeopleApiAccessible() {
        val api = TmdbApi(apiKey = "test-api-key")
        val people = api.people
        assertNotNull(people)
        api.close()
    }

    @Test
    fun testMultiplePeopleInstancesAreSame() {
        val api = TmdbApi(apiKey = "test-api-key")
        val people1 = api.people
        val people2 = api.people
        assertEquals(people1, people2, "People API should use lazy initialization")
        api.close()
    }
}

/**
 * Tests for main TmdbApi class initialization.
 */
class TmdbApiInitializationTest {

    @Test
    fun testApiCreationWithDefaultParameters() {
        val api = TmdbApi(apiKey = "test-key")
        assertNotNull(api)
        api.close()
    }

    @Test
    fun testApiCreationWithCustomBaseUrl() {
        val api = TmdbApi(
            apiKey = "test-key",
            baseUrl = "https://custom.api.url"
        )
        assertNotNull(api)
        api.close()
    }

    @Test
    fun testApiCreationWithLoggingEnabled() {
        val api = TmdbApi(
            apiKey = "test-key",
            enableLogging = true
        )
        assertNotNull(api)
        api.close()
    }

    @Test
    fun testAllApisAccessible() {
        val api = TmdbApi(apiKey = "test-key")

        assertNotNull(api.movies, "Movies API should be accessible")
        assertNotNull(api.search, "Search API should be accessible")
        assertNotNull(api.trending, "Trending API should be accessible")
        assertNotNull(api.configuration, "Configuration API should be accessible")
        assertNotNull(api.genres, "Genres API should be accessible")
        assertNotNull(api.collections, "Collections API should be accessible")
        assertNotNull(api.people, "People API should be accessible")

        api.close()
    }

    @Test
    fun testLazyInitialization() {
        val api = TmdbApi(apiKey = "test-key")

        // Access each API multiple times
        val movies1 = api.movies
        val movies2 = api.movies
        assertEquals(movies1, movies2, "Should return same instance")

        val search1 = api.search
        val search2 = api.search
        assertEquals(search1, search2, "Should return same instance")

        api.close()
    }

    @Test
    fun testApiCloseDoesNotThrow() {
        val api = TmdbApi(apiKey = "test-key")
        // Should not throw
        api.close()
        api.close() // Calling close multiple times should be safe
    }
}
