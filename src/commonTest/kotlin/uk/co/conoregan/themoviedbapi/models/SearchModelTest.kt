package uk.co.conoregan.themoviedbapi.models

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.model.search.MultiSearchResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Tests for Search model serialization/deserialization.
 */
class SearchModelTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testMultiSearchResultMovieDeserialization() {
        val jsonString = """
            {
                "media_type": "movie",
                "id": 550,
                "title": "Fight Club",
                "original_title": "Fight Club",
                "original_language": "en",
                "release_date": "1999-10-15",
                "vote_average": 8.4,
                "vote_count": 27310,
                "adult": false,
                "backdrop_path": "/path.jpg",
                "genre_ids": [18],
                "overview": "A ticking-time-bomb insomniac...",
                "popularity": 61.416,
                "poster_path": "/poster.jpg",
                "video": false
            }
        """.trimIndent()

        val result = json.decodeFromString<MultiSearchResult>(jsonString)

        assertEquals("movie", result.mediaType)
        assertEquals(550, result.id)
        assertEquals("Fight Club", result.title)

        // Test conversion to movie
        val movie = result.asMovie()
        assertNotNull(movie)
        assertEquals(550, movie.id)
        assertEquals("Fight Club", movie.title)

        // Should not convert to other types
        assertNull(result.asTvSeries())
        assertNull(result.asPerson())
    }

    @Test
    fun testMultiSearchResultTvDeserialization() {
        val jsonString = """
            {
                "media_type": "tv",
                "id": 1396,
                "name": "Breaking Bad",
                "original_name": "Breaking Bad",
                "original_language": "en",
                "first_air_date": "2008-01-20",
                "vote_average": 8.9,
                "vote_count": 12500,
                "adult": false,
                "backdrop_path": "/path.jpg",
                "genre_ids": [18, 80],
                "origin_country": ["US"],
                "overview": "A high school chemistry teacher...",
                "popularity": 200.5,
                "poster_path": "/poster.jpg"
            }
        """.trimIndent()

        val result = json.decodeFromString<MultiSearchResult>(jsonString)

        assertEquals("tv", result.mediaType)
        assertEquals(1396, result.id)
        assertEquals("Breaking Bad", result.name)

        // Test conversion to TV series
        val tvSeries = result.asTvSeries()
        assertNotNull(tvSeries)
        assertEquals(1396, tvSeries.id)
        assertEquals("Breaking Bad", tvSeries.name)

        // Should not convert to other types
        assertNull(result.asMovie())
        assertNull(result.asPerson())
    }

    @Test
    fun testMultiSearchResultPersonDeserialization() {
        val jsonString = """
            {
                "media_type": "person",
                "id": 287,
                "name": "Brad Pitt",
                "original_name": "Brad Pitt",
                "adult": false,
                "gender": 2,
                "known_for_department": "Acting",
                "popularity": 45.6,
                "profile_path": "/profile.jpg"
            }
        """.trimIndent()

        val result = json.decodeFromString<MultiSearchResult>(jsonString)

        assertEquals("person", result.mediaType)
        assertEquals(287, result.id)
        assertEquals("Brad Pitt", result.name)

        // Test conversion to person
        val person = result.asPerson()
        assertNotNull(person)
        assertEquals(287, person.id)
        assertEquals("Brad Pitt", person.name)

        // Should not convert to other types
        assertNull(result.asMovie())
        assertNull(result.asTvSeries())
    }
}
