package uk.co.conoregan.themoviedbapi

import uk.co.conoregan.themoviedbapi.api.TmdbApi
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests for TmdbMovies API.
 *
 * Note: These are basic structural tests. For full integration testing,
 * you would need a valid API key and network access.
 */
class TmdbMoviesTest {
    @Test
    fun testMoviesApiCreation() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api.movies)
        api.close()
    }

    @Test
    fun testApiHasAllEndpoints() {
        val api = TmdbApi(apiKey = "test-api-key")

        // Verify all API endpoints are accessible
        assertNotNull(api.movies, "Movies API should be available")
        assertNotNull(api.search, "Search API should be available")
        assertNotNull(api.trending, "Trending API should be available")
        assertNotNull(api.configuration, "Configuration API should be available")
        assertNotNull(api.genres, "Genres API should be available")
        assertNotNull(api.collections, "Collections API should be available")

        api.close()
    }
}
