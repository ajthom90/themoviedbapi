package uk.co.conoregan.themoviedbapi.api

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.client.TmdbSerializationException
import uk.co.conoregan.themoviedbapi.model.people.*
import uk.co.conoregan.themoviedbapi.util.ApiUrl

/**
 * TMDb API endpoints for people (actors, directors, crew).
 *
 * Get person details, filmography, images, and more.
 *
 * See the [TMDb People API documentation](https://developer.themoviedb.org/reference/person-details) for more info.
 */
class TmdbPeople internal constructor(private val api: TmdbApi) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Get the top level details of a person.
     *
     * @param personId The TMDb ID of the person.
     * @param language The language to query results in (default: en-US).
     * @param appendToResponse Additional namespaces to append to the result (max 20).
     *        Available options: "combined_credits", "external_ids", "images",
     *        "movie_credits", "tv_credits", "translations"
     * @return The person details.
     *
     * Example:
     * ```kotlin
     * // Basic usage
     * val person = tmdb.people.getDetails(287) // Brad Pitt
     *
     * // With append to response
     * val person = tmdb.people.getDetails(
     *     personId = 287,
     *     appendToResponse = arrayOf("combined_credits", "external_ids", "images")
     * )
     * println("${person.name} - ${person.knownForDepartment}")
     * person.combinedCredits?.cast?.forEach { println(it.title ?: it.name) }
     * ```
     */
    suspend fun getDetails(
        personId: Int,
        language: String? = null,
        vararg appendToResponse: String
    ): Person {
        val apiUrl = ApiUrl("person", personId)
        apiUrl.addLanguage(language)
        apiUrl.addAppendToResponse(*appendToResponse)

        return request(apiUrl)
    }

    /**
     * Get the combined movie and TV credits for a person.
     *
     * This includes both cast and crew roles across all movies and TV shows.
     *
     * @param personId The TMDb ID of the person.
     * @param language The language to query results in (default: en-US).
     * @return The combined credits (movies and TV).
     *
     * Example:
     * ```kotlin
     * val credits = tmdb.people.getCombinedCredits(287) // Brad Pitt
     * println("Cast roles: ${credits.cast.size}")
     * println("Crew roles: ${credits.crew.size}")
     *
     * // Filter by media type
     * val movies = credits.cast.filter { it.mediaType == "movie" }
     * val tvShows = credits.cast.filter { it.mediaType == "tv" }
     * ```
     */
    suspend fun getCombinedCredits(
        personId: Int,
        language: String? = null
    ): CombinedCredits {
        val apiUrl = ApiUrl("person", personId, "combined_credits")
        apiUrl.addLanguage(language)

        return request(apiUrl)
    }

    /**
     * Get the movie credits for a person.
     *
     * @param personId The TMDb ID of the person.
     * @param language The language to query results in (default: en-US).
     * @return The movie credits (cast and crew).
     *
     * Example:
     * ```kotlin
     * val credits = tmdb.people.getMovieCredits(287)
     * credits.cast.forEach { movie ->
     *     println("${movie.title} (${movie.releaseDate}) as ${movie.character}")
     * }
     * ```
     */
    suspend fun getMovieCredits(
        personId: Int,
        language: String? = null
    ): MovieCredits {
        val apiUrl = ApiUrl("person", personId, "movie_credits")
        apiUrl.addLanguage(language)

        return request(apiUrl)
    }

    /**
     * Get the TV credits for a person.
     *
     * @param personId The TMDb ID of the person.
     * @param language The language to query results in (default: en-US).
     * @return The TV credits (cast and crew).
     *
     * Example:
     * ```kotlin
     * val credits = tmdb.people.getTvCredits(287)
     * credits.cast.forEach { tvShow ->
     *     println("${tvShow.name} (${tvShow.episodeCount} episodes) as ${tvShow.character}")
     * }
     * ```
     */
    suspend fun getTvCredits(
        personId: Int,
        language: String? = null
    ): TvCredits {
        val apiUrl = ApiUrl("person", personId, "tv_credits")
        apiUrl.addLanguage(language)

        return request(apiUrl)
    }

    /**
     * Get the external IDs for a person.
     *
     * @param personId The TMDb ID of the person.
     * @return The external IDs (IMDB, Instagram, Twitter, etc.).
     *
     * Example:
     * ```kotlin
     * val externalIds = tmdb.people.getExternalIds(287)
     * println("IMDB: ${externalIds.imdbId}")
     * println("Instagram: ${externalIds.instagramId}")
     * println("Twitter: ${externalIds.twitterId}")
     * ```
     */
    suspend fun getExternalIds(personId: Int): PersonExternalIds {
        val apiUrl = ApiUrl("person", personId, "external_ids")
        return request(apiUrl)
    }

    /**
     * Get the profile images for a person.
     *
     * @param personId The TMDb ID of the person.
     * @return The profile images.
     *
     * Example:
     * ```kotlin
     * val images = tmdb.people.getImages(287)
     * println("Profile photos: ${images.profiles.size}")
     * images.profiles.forEach { photo ->
     *     println("${photo.width}x${photo.height}: ${photo.filePath}")
     * }
     * ```
     */
    suspend fun getImages(personId: Int): PersonImages {
        val apiUrl = ApiUrl("person", personId, "images")
        return request(apiUrl)
    }

    /**
     * Get the translations for a person.
     *
     * @param personId The TMDb ID of the person.
     * @return The translations (biographies in different languages).
     *
     * Example:
     * ```kotlin
     * val translations = tmdb.people.getTranslations(287)
     * translations.translations.forEach { translation ->
     *     println("${translation.englishName}: ${translation.data?.biography?.take(100)}...")
     * }
     * ```
     */
    suspend fun getTranslations(personId: Int): PersonTranslations {
        val apiUrl = ApiUrl("person", personId, "translations")
        return request(apiUrl)
    }

    /**
     * Get the latest person added to TMDb.
     *
     * This is a live response and will continuously change.
     *
     * @return The latest person.
     *
     * Example:
     * ```kotlin
     * val latestPerson = tmdb.people.getLatest()
     * println("Latest person added: ${latestPerson.name}")
     * ```
     */
    suspend fun getLatest(): Person {
        val apiUrl = ApiUrl("person", "latest")
        return request(apiUrl)
    }

    /**
     * Make a request to the TMDb API and parse the response.
     */
    private suspend inline fun <reified T> request(apiUrl: ApiUrl): T {
        val response = api.httpClient.get(
            path = apiUrl.getPath(),
            parameters = apiUrl.getParameters()
        )

        return try {
            json.decodeFromString<T>(response)
        } catch (e: Exception) {
            throw TmdbSerializationException("Failed to parse response: ${e.message}", e)
        }
    }
}
