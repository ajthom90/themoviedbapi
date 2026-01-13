package uk.co.conoregan.themoviedbapi

import uk.co.conoregan.themoviedbapi.api.TmdbApi
import kotlin.test.Test
import kotlin.test.assertNotNull

class TmdbApiTest {
    @Test
    fun testApiCreation() {
        val api = TmdbApi(apiKey = "test-api-key")
        assertNotNull(api)
        assertNotNull(api.movies)
        api.close()
    }
}
