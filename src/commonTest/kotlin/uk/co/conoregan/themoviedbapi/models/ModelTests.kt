package uk.co.conoregan.themoviedbapi.models

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.model.core.*
import uk.co.conoregan.themoviedbapi.model.configuration.*
import uk.co.conoregan.themoviedbapi.model.collections.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests for core model classes.
 */
class CoreModelTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testGenreDeserialization() {
        val jsonString = """
            {
                "id": 28,
                "name": "Action"
            }
        """.trimIndent()

        val genre = json.decodeFromString<Genre>(jsonString)
        assertEquals(28, genre.id)
        assertEquals("Action", genre.name)
    }

    @Test
    fun testProductionCompanyDeserialization() {
        val jsonString = """
            {
                "id": 33,
                "logo_path": "/logo.png",
                "name": "Universal Pictures",
                "origin_country": "US"
            }
        """.trimIndent()

        val company = json.decodeFromString<ProductionCompany>(jsonString)
        assertEquals(33, company.id)
        assertEquals("Universal Pictures", company.name)
        assertEquals("/logo.png", company.logoPath)
        assertEquals("US", company.originCountry)
    }

    @Test
    fun testProductionCountryDeserialization() {
        val jsonString = """
            {
                "iso_3166_1": "US",
                "name": "United States of America"
            }
        """.trimIndent()

        val country = json.decodeFromString<ProductionCountry>(jsonString)
        assertEquals("US", country.iso31661)
        assertEquals("United States of America", country.name)
    }

    @Test
    fun testLanguageDeserialization() {
        val jsonString = """
            {
                "english_name": "English",
                "iso_639_1": "en",
                "name": "English"
            }
        """.trimIndent()

        val language = json.decodeFromString<Language>(jsonString)
        assertEquals("English", language.englishName)
        assertEquals("en", language.iso6391)
        assertEquals("English", language.name)
    }

    @Test
    fun testAccountStatesDeserialization() {
        val jsonString = """
            {
                "id": 550,
                "favorite": true,
                "rated": {
                    "value": 8.5
                },
                "watchlist": false
            }
        """.trimIndent()

        val states = json.decodeFromString<AccountStates>(jsonString)
        assertEquals(550, states.id)
        assertTrue(states.favorite)
        assertEquals(8.5, states.rated?.value)
        assertEquals(false, states.watchlist)
    }

    @Test
    fun testAccountStatesWithMissingRated() {
        val jsonString = """
            {
                "favorite": false,
                "watchlist": true
            }
        """.trimIndent()

        val states = json.decodeFromString<AccountStates>(jsonString)
        assertEquals(false, states.favorite)
        assertEquals(true, states.watchlist)
        assertEquals(null, states.rated)
    }

    @Test
    fun testResponseStatusDeserialization() {
        val jsonString = """
            {
                "status_code": 1,
                "status_message": "Success",
                "success": true
            }
        """.trimIndent()

        val response = json.decodeFromString<ResponseStatus>(jsonString)
        assertEquals(1, response.statusCode)
        assertEquals("Success", response.statusMessage)
        assertTrue(response.success)
    }
}

/**
 * Tests for Configuration models.
 */
class ConfigurationModelTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testConfigurationDeserialization() {
        val jsonString = """
            {
                "images": {
                    "base_url": "http://image.tmdb.org/t/p/",
                    "secure_base_url": "https://image.tmdb.org/t/p/",
                    "backdrop_sizes": ["w300", "w780", "w1280", "original"],
                    "logo_sizes": ["w45", "w92", "w154", "w185", "w300", "w500", "original"],
                    "poster_sizes": ["w92", "w154", "w185", "w342", "w500", "w780", "original"],
                    "profile_sizes": ["w45", "w185", "h632", "original"],
                    "still_sizes": ["w92", "w185", "w300", "original"]
                },
                "change_keys": ["adult", "air_date", "also_known_as"]
            }
        """.trimIndent()

        val config = json.decodeFromString<Configuration>(jsonString)
        assertEquals("https://image.tmdb.org/t/p/", config.images.secureBaseUrl)
        assertEquals(4, config.images.backdropSizes.size)
        assertEquals(7, config.images.posterSizes.size)
        assertEquals(3, config.changeKeys.size)
    }

    @Test
    fun testCountryDeserialization() {
        val jsonString = """
            {
                "iso_3166_1": "US",
                "english_name": "United States of America",
                "native_name": "United States of America"
            }
        """.trimIndent()

        val country = json.decodeFromString<Country>(jsonString)
        assertEquals("US", country.iso31661)
        assertEquals("United States of America", country.englishName)
        assertEquals("United States of America", country.nativeName)
    }

    @Test
    fun testDepartmentDeserialization() {
        val jsonString = """
            {
                "department": "Production",
                "jobs": ["Producer", "Executive Producer", "Co-Producer"]
            }
        """.trimIndent()

        val dept = json.decodeFromString<Department>(jsonString)
        assertEquals("Production", dept.department)
        assertEquals(3, dept.jobs.size)
        assertTrue(dept.jobs.contains("Producer"))
    }

    @Test
    fun testTimezoneDeserialization() {
        val jsonString = """
            {
                "iso_3166_1": "US",
                "zones": ["America/New_York", "America/Los_Angeles", "America/Chicago"]
            }
        """.trimIndent()

        val timezone = json.decodeFromString<Timezone>(jsonString)
        assertEquals("US", timezone.iso31661)
        assertEquals(3, timezone.zones.size)
    }
}

/**
 * Tests for Collection models.
 */
class CollectionModelTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testCollectionDeserialization() {
        val jsonString = """
            {
                "id": 119,
                "name": "The Lord of the Rings Collection",
                "overview": "The Lord of the Rings is an epic...",
                "poster_path": "/poster.jpg",
                "backdrop_path": "/backdrop.jpg",
                "parts": [
                    {
                        "id": 120,
                        "title": "The Fellowship of the Ring",
                        "original_title": "The Fellowship of the Ring",
                        "original_language": "en",
                        "release_date": "2001-12-18",
                        "vote_average": 8.4,
                        "vote_count": 20000
                    },
                    {
                        "id": 121,
                        "title": "The Two Towers",
                        "original_title": "The Two Towers",
                        "original_language": "en",
                        "release_date": "2002-12-18",
                        "vote_average": 8.3,
                        "vote_count": 18000
                    }
                ]
            }
        """.trimIndent()

        val collection = json.decodeFromString<Collection>(jsonString)
        assertEquals(119, collection.id)
        assertEquals("The Lord of the Rings Collection", collection.name)
        assertEquals(2, collection.parts.size)
        assertEquals("The Fellowship of the Ring", collection.parts[0].title)
        assertEquals("The Two Towers", collection.parts[1].title)
    }

    @Test
    fun testCollectionWithoutParts() {
        val jsonString = """
            {
                "id": 119,
                "name": "Test Collection",
                "parts": []
            }
        """.trimIndent()

        val collection = json.decodeFromString<Collection>(jsonString)
        assertEquals(119, collection.id)
        assertTrue(collection.parts.isEmpty())
    }
}

/**
 * Tests for TV Series models.
 */
class TvSeriesModelTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testTvSeriesSummaryDeserialization() {
        val jsonString = """
            {
                "id": 1396,
                "name": "Breaking Bad",
                "original_name": "Breaking Bad",
                "original_language": "en",
                "first_air_date": "2008-01-20",
                "vote_average": 8.9,
                "vote_count": 12000,
                "adult": false,
                "backdrop_path": "/backdrop.jpg",
                "genre_ids": [18, 80],
                "origin_country": ["US"],
                "overview": "A high school chemistry teacher...",
                "popularity": 200.5,
                "poster_path": "/poster.jpg"
            }
        """.trimIndent()

        val tvSeries = json.decodeFromString<uk.co.conoregan.themoviedbapi.model.tv.TvSeriesSummary>(jsonString)
        assertEquals(1396, tvSeries.id)
        assertEquals("Breaking Bad", tvSeries.name)
        assertEquals("Breaking Bad", tvSeries.originalName)
        assertEquals("2008-01-20", tvSeries.firstAirDate)
        assertEquals(8.9, tvSeries.voteAverage)
        assertEquals(2, tvSeries.genreIds.size)
        assertEquals(1, tvSeries.originCountry.size)
        assertEquals("US", tvSeries.originCountry[0])
    }

    @Test
    fun testTvSeriesWithMissingOptionalFields() {
        val jsonString = """
            {
                "id": 1396,
                "name": "Test Show",
                "original_name": "Test Show",
                "original_language": "en"
            }
        """.trimIndent()

        val tvSeries = json.decodeFromString<uk.co.conoregan.themoviedbapi.model.tv.TvSeriesSummary>(jsonString)
        assertEquals(1396, tvSeries.id)
        assertEquals("Test Show", tvSeries.name)
        assertEquals(0.0, tvSeries.voteAverage)
        assertEquals(0, tvSeries.voteCount)
        assertTrue(tvSeries.genreIds.isEmpty())
        assertTrue(tvSeries.originCountry.isEmpty())
    }
}

/**
 * Tests for edge cases and error handling in models.
 */
class ModelEdgeCasesTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testEmptyResultsPageDeserialization() {
        val jsonString = """
            {
                "page": 1,
                "results": [],
                "total_pages": 0,
                "total_results": 0
            }
        """.trimIndent()

        val resultsPage = json.decodeFromString<ResultsPage<uk.co.conoregan.themoviedbapi.model.movies.MovieSummary>>(jsonString)
        assertEquals(1, resultsPage.page)
        assertTrue(resultsPage.results.isEmpty())
        assertEquals(0, resultsPage.totalPages)
        assertEquals(0, resultsPage.totalResults)
    }

    @Test
    fun testModelWithUnknownFields() {
        val jsonString = """
            {
                "id": 28,
                "name": "Action",
                "unknown_field": "should be ignored",
                "another_unknown": 12345
            }
        """.trimIndent()

        // Should not throw, unknown fields should be ignored
        val genre = json.decodeFromString<Genre>(jsonString)
        assertEquals(28, genre.id)
        assertEquals("Action", genre.name)
    }

    @Test
    fun testModelWithNullOptionalFields() {
        val jsonString = """
            {
                "id": 550,
                "favorite": false,
                "rated": null,
                "watchlist": false
            }
        """.trimIndent()

        val states = json.decodeFromString<AccountStates>(jsonString)
        assertEquals(null, states.rated)
        assertEquals(false, states.favorite)
    }
}
