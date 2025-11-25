package uk.co.conoregan.themoviedbapi.models

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.model.movies.MovieSummary
import uk.co.conoregan.themoviedbapi.model.core.ResultsPage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Tests for Movie model serialization/deserialization.
 */
class MovieModelTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testMovieSummaryDeserialization() {
        val jsonString = """
            {
                "id": 550,
                "adult": false,
                "backdrop_path": "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
                "genre_ids": [18],
                "original_language": "en",
                "original_title": "Fight Club",
                "overview": "A ticking-time-bomb insomniac...",
                "popularity": 61.416,
                "poster_path": "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
                "release_date": "1999-10-15",
                "title": "Fight Club",
                "video": false,
                "vote_average": 8.433,
                "vote_count": 27310
            }
        """.trimIndent()

        val movie = json.decodeFromString<MovieSummary>(jsonString)

        assertEquals(550, movie.id)
        assertEquals("Fight Club", movie.title)
        assertEquals("Fight Club", movie.originalTitle)
        assertEquals("1999-10-15", movie.releaseDate)
        assertEquals(8.433, movie.voteAverage)
        assertEquals(27310, movie.voteCount)
    }

    @Test
    fun testResultsPageDeserialization() {
        val jsonString = """
            {
                "page": 1,
                "results": [
                    {
                        "id": 550,
                        "title": "Fight Club",
                        "original_title": "Fight Club",
                        "original_language": "en",
                        "vote_average": 8.4,
                        "vote_count": 27310
                    }
                ],
                "total_pages": 1,
                "total_results": 1
            }
        """.trimIndent()

        val resultsPage = json.decodeFromString<ResultsPage<MovieSummary>>(jsonString)

        assertEquals(1, resultsPage.page)
        assertEquals(1, resultsPage.totalPages)
        assertEquals(1, resultsPage.totalResults)
        assertEquals(1, resultsPage.results.size)
        assertEquals("Fight Club", resultsPage.results[0].title)
    }

    @Test
    fun testMovieSummaryWithMissingFields() {
        val jsonString = """
            {
                "id": 550,
                "title": "Fight Club",
                "original_title": "Fight Club",
                "original_language": "en"
            }
        """.trimIndent()

        val movie = json.decodeFromString<MovieSummary>(jsonString)

        assertEquals(550, movie.id)
        assertEquals("Fight Club", movie.title)
        assertEquals(0.0, movie.voteAverage) // Default value
        assertEquals(0, movie.voteCount) // Default value
        assertEquals(false, movie.adult) // Default value
    }
}
