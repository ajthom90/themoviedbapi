package uk.co.conoregan.themoviedbapi.models

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.model.people.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

/**
 * Tests for People model serialization/deserialization.
 */
class PeopleModelTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Test
    fun testPersonDeserialization() {
        val jsonString = """
            {
                "id": 287,
                "name": "Brad Pitt",
                "adult": false,
                "also_known_as": ["William Bradley Pitt"],
                "biography": "An American actor and film producer...",
                "birthday": "1963-12-18",
                "deathday": null,
                "gender": 2,
                "homepage": null,
                "imdb_id": "nm0000093",
                "known_for_department": "Acting",
                "place_of_birth": "Shawnee, Oklahoma, USA",
                "popularity": 45.123,
                "profile_path": "/path.jpg"
            }
        """.trimIndent()

        val person = json.decodeFromString<Person>(jsonString)

        assertEquals(287, person.id)
        assertEquals("Brad Pitt", person.name)
        assertEquals(2, person.gender)
        assertEquals("Male", person.getGenderString())
        assertEquals("1963-12-18", person.birthday)
        assertEquals("Shawnee, Oklahoma, USA", person.placeOfBirth)
        assertEquals("Acting", person.knownForDepartment)
        assertEquals("nm0000093", person.imdbId)
        assertTrue(person.isAlive)
        assertEquals(1, person.alsoKnownAs.size)
        assertEquals("William Bradley Pitt", person.alsoKnownAs.first())
    }

    @Test
    fun testPersonWithDeathday() {
        val jsonString = """
            {
                "id": 123,
                "name": "Test Person",
                "birthday": "1950-01-01",
                "deathday": "2020-12-31",
                "gender": 1
            }
        """.trimIndent()

        val person = json.decodeFromString<Person>(jsonString)

        assertEquals("Female", person.getGenderString())
        assertEquals("2020-12-31", person.deathday)
        assertFalse(person.isAlive)
    }

    @Test
    fun testPersonGenderStrings() {
        val genderTests = mapOf(
            0 to "Not specified",
            1 to "Female",
            2 to "Male",
            3 to "Non-binary",
            null to "Not specified"
        )

        genderTests.forEach { (genderValue, expected) ->
            val jsonString = """
                {
                    "id": 1,
                    "name": "Test",
                    ${if (genderValue != null) "\"gender\": $genderValue," else ""}
                    "adult": false
                }
            """.trimIndent()

            val person = json.decodeFromString<Person>(jsonString)
            assertEquals(expected, person.getGenderString())
        }
    }

    @Test
    fun testMovieCreditsDeserialization() {
        val jsonString = """
            {
                "cast": [
                    {
                        "id": 550,
                        "credit_id": "52fe4250c3a36847f8014a9f",
                        "character": "Tyler Durden",
                        "order": 0,
                        "original_language": "en",
                        "original_title": "Fight Club",
                        "title": "Fight Club",
                        "release_date": "1999-10-15",
                        "vote_average": 8.4,
                        "vote_count": 27000
                    }
                ],
                "crew": [
                    {
                        "id": 123,
                        "credit_id": "abc123",
                        "department": "Production",
                        "job": "Producer",
                        "original_language": "en",
                        "original_title": "Test Movie",
                        "title": "Test Movie"
                    }
                ]
            }
        """.trimIndent()

        val credits = json.decodeFromString<MovieCredits>(jsonString)

        assertEquals(1, credits.cast.size)
        assertEquals(1, credits.crew.size)

        val castMember = credits.cast.first()
        assertEquals(550, castMember.id)
        assertEquals("Tyler Durden", castMember.character)
        assertEquals("Fight Club", castMember.title)
        assertEquals("1999-10-15", castMember.releaseDate)

        val crewMember = credits.crew.first()
        assertEquals("Production", crewMember.department)
        assertEquals("Producer", crewMember.job)
    }

    @Test
    fun testTvCreditsDeserialization() {
        val jsonString = """
            {
                "cast": [
                    {
                        "id": 1396,
                        "credit_id": "xyz789",
                        "character": "Main Character",
                        "episode_count": 62,
                        "original_language": "en",
                        "original_name": "Breaking Bad",
                        "name": "Breaking Bad",
                        "first_air_date": "2008-01-20",
                        "vote_average": 8.9,
                        "vote_count": 12000
                    }
                ],
                "crew": []
            }
        """.trimIndent()

        val credits = json.decodeFromString<TvCredits>(jsonString)

        assertEquals(1, credits.cast.size)
        assertEquals(0, credits.crew.size)

        val castMember = credits.cast.first()
        assertEquals(1396, castMember.id)
        assertEquals("Main Character", castMember.character)
        assertEquals(62, castMember.episodeCount)
        assertEquals("Breaking Bad", castMember.name)
    }

    @Test
    fun testCombinedCreditsDeserialization() {
        val jsonString = """
            {
                "cast": [
                    {
                        "id": 550,
                        "media_type": "movie",
                        "credit_id": "abc123",
                        "character": "Tyler Durden",
                        "original_title": "Fight Club",
                        "title": "Fight Club",
                        "release_date": "1999-10-15",
                        "original_language": "en"
                    },
                    {
                        "id": 1396,
                        "media_type": "tv",
                        "credit_id": "xyz789",
                        "character": "Main Character",
                        "original_name": "Breaking Bad",
                        "name": "Breaking Bad",
                        "first_air_date": "2008-01-20",
                        "episode_count": 62,
                        "original_language": "en"
                    }
                ],
                "crew": []
            }
        """.trimIndent()

        val credits = json.decodeFromString<CombinedCredits>(jsonString)

        assertEquals(2, credits.cast.size)
        assertEquals(0, credits.crew.size)

        val movieCredit = credits.cast.first { it.mediaType == "movie" }
        assertEquals("Fight Club", movieCredit.title)
        assertNotNull(movieCredit.releaseDate)

        val tvCredit = credits.cast.first { it.mediaType == "tv" }
        assertEquals("Breaking Bad", tvCredit.name)
        assertEquals(62, tvCredit.episodeCount)
    }

    @Test
    fun testPersonExternalIdsDeserialization() {
        val jsonString = """
            {
                "id": 287,
                "imdb_id": "nm0000093",
                "facebook_id": "bradpitt",
                "instagram_id": "bradpittofficial",
                "twitter_id": "bradpitt",
                "wikidata_id": "Q35332"
            }
        """.trimIndent()

        val externalIds = json.decodeFromString<PersonExternalIds>(jsonString)

        assertEquals("nm0000093", externalIds.imdbId)
        assertEquals("bradpitt", externalIds.facebookId)
        assertEquals("bradpittofficial", externalIds.instagramId)
        assertEquals("bradpitt", externalIds.twitterId)
        assertEquals("Q35332", externalIds.wikidataId)
    }

    @Test
    fun testPersonImagesDeserialization() {
        val jsonString = """
            {
                "profiles": [
                    {
                        "aspect_ratio": 0.667,
                        "file_path": "/profile1.jpg",
                        "height": 3000,
                        "width": 2000,
                        "vote_average": 5.5,
                        "vote_count": 10
                    },
                    {
                        "aspect_ratio": 0.667,
                        "file_path": "/profile2.jpg",
                        "height": 1500,
                        "width": 1000,
                        "vote_average": 5.2,
                        "vote_count": 5
                    }
                ]
            }
        """.trimIndent()

        val images = json.decodeFromString<PersonImages>(jsonString)

        assertEquals(2, images.profiles.size)

        val largeProfile = images.profiles.first()
        assertEquals("/profile1.jpg", largeProfile.filePath)
        assertEquals(3000, largeProfile.height)
        assertEquals(2000, largeProfile.width)
        assertEquals(0.667, largeProfile.aspectRatio)
    }

    @Test
    fun testPersonTranslationsDeserialization() {
        val jsonString = """
            {
                "translations": [
                    {
                        "iso_3166_1": "US",
                        "iso_639_1": "en",
                        "name": "English",
                        "english_name": "English",
                        "data": {
                            "biography": "English biography..."
                        }
                    },
                    {
                        "iso_3166_1": "FR",
                        "iso_639_1": "fr",
                        "name": "Français",
                        "english_name": "French",
                        "data": {
                            "biography": "Biographie française..."
                        }
                    }
                ]
            }
        """.trimIndent()

        val translations = json.decodeFromString<PersonTranslations>(jsonString)

        assertEquals(2, translations.translations.size)

        val englishTranslation = translations.translations.first { it.iso6391 == "en" }
        assertEquals("English", englishTranslation.name)
        assertNotNull(englishTranslation.data?.biography)
        assertTrue(englishTranslation.data?.biography?.startsWith("English") == true)

        val frenchTranslation = translations.translations.first { it.iso6391 == "fr" }
        assertEquals("Français", frenchTranslation.name)
        assertEquals("French", frenchTranslation.englishName)
    }

    @Test
    fun testPersonWithMissingOptionalFields() {
        val jsonString = """
            {
                "id": 123,
                "name": "Test Person"
            }
        """.trimIndent()

        val person = json.decodeFromString<Person>(jsonString)

        assertEquals(123, person.id)
        assertEquals("Test Person", person.name)
        assertFalse(person.adult)
        assertEquals(0.0, person.popularity)
        assertEquals("Not specified", person.getGenderString())
        assertTrue(person.alsoKnownAs.isEmpty())
        assertTrue(person.isAlive)
    }

    @Test
    fun testCombinedCastMediaTypeFiltering() {
        val jsonString = """
            {
                "cast": [
                    {"id": 1, "media_type": "movie", "credit_id": "1", "title": "Movie 1", "original_language": "en"},
                    {"id": 2, "media_type": "tv", "credit_id": "2", "name": "TV 1", "original_language": "en"},
                    {"id": 3, "media_type": "movie", "credit_id": "3", "title": "Movie 2", "original_language": "en"},
                    {"id": 4, "media_type": "tv", "credit_id": "4", "name": "TV 2", "original_language": "en"}
                ],
                "crew": []
            }
        """.trimIndent()

        val credits = json.decodeFromString<CombinedCredits>(jsonString)

        val movieCredits = credits.cast.filter { it.mediaType == "movie" }
        val tvCredits = credits.cast.filter { it.mediaType == "tv" }

        assertEquals(2, movieCredits.size)
        assertEquals(2, tvCredits.size)

        assertTrue(movieCredits.all { it.title != null })
        assertTrue(tvCredits.all { it.name != null })
    }
}
