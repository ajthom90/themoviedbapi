package uk.co.conoregan.themoviedbapi.api

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.client.TmdbSerializationException
import uk.co.conoregan.themoviedbapi.model.core.AccountStates
import uk.co.conoregan.themoviedbapi.model.core.ResultsPage
import uk.co.conoregan.themoviedbapi.model.movies.*
import uk.co.conoregan.themoviedbapi.util.ApiUrl

/**
 * TMDb API endpoints for movies.
 *
 * See the [TMDb Movie API documentation](https://developer.themoviedb.org/reference/movie-details) for more info.
 */
class TmdbMovies internal constructor(private val api: TmdbApi) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Get the top level details of a movie by ID.
     *
     * @param movieId The TMDb id of the movie.
     * @param language The language to query the results in (default: en-US). Format: ISO 639-1 code with optional region.
     * @param appendToResponse Additional namespaces to append to the result (max 20).
     *        Available options: "account_states", "alternative_titles", "credits", "external_ids",
     *        "images", "keywords", "lists", "recommendations", "release_dates", "reviews",
     *        "similar", "translations", "videos", "watch/providers"
     * @return The movie details.
     *
     * Example:
     * ```kotlin
     * // Basic usage
     * val movie = tmdb.movies.getDetails(550)
     *
     * // With language
     * val movie = tmdb.movies.getDetails(550, language = "fr-FR")
     *
     * // With append to response
     * val movie = tmdb.movies.getDetails(
     *     movieId = 550,
     *     appendToResponse = arrayOf("credits", "videos", "images")
     * )
     * ```
     */
    suspend fun getDetails(
        movieId: Int,
        language: String? = null,
        vararg appendToResponse: String
    ): Movie {
        val apiUrl = ApiUrl("movie", movieId)
        apiUrl.addLanguage(language)
        apiUrl.addAppendToResponse(*appendToResponse)

        return request(apiUrl)
    }

    /**
     * Get the rating, watchlist and favourite status of an account for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @param sessionId The session id of the user (required if not using guest session).
     * @param guestSessionId The guest session id of the user (required if not using regular session).
     * @return The account states of the movie.
     */
    suspend fun getAccountStates(
        movieId: Int,
        sessionId: String? = null,
        guestSessionId: String? = null
    ): AccountStates {
        val apiUrl = ApiUrl("movie", movieId, "account_states")
        apiUrl.addParam("session_id", sessionId)
        apiUrl.addParam("guest_session_id", guestSessionId)

        return request(apiUrl)
    }

    /**
     * Get the alternative titles for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @param country Filter the results by country (ISO-3166-1 code), e.g., "US".
     * @return The alternative titles of the movie.
     */
    suspend fun getAlternativeTitles(
        movieId: Int,
        country: String? = null
    ): AlternativeTitles {
        val apiUrl = ApiUrl("movie", movieId, "alternative_titles")
        apiUrl.addParam("country", country)

        return request(apiUrl)
    }

    /**
     * Get the credits (cast and crew) for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @param language The language to query the results in.
     * @return The credits (cast and crew) of the movie.
     */
    suspend fun getCredits(
        movieId: Int,
        language: String? = null
    ): Credits {
        val apiUrl = ApiUrl("movie", movieId, "credits")
        apiUrl.addLanguage(language)

        return request(apiUrl)
    }

    /**
     * Get the external IDs for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @return The external IDs of the movie.
     */
    suspend fun getExternalIds(movieId: Int): ExternalIds {
        val apiUrl = ApiUrl("movie", movieId, "external_ids")
        return request(apiUrl)
    }

    /**
     * Get the images (posters, backdrops, logos) for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @param language Filter images by language. Use null for all languages.
     * @param includeImageLanguage Include additional image languages (comma-separated).
     * @return The images of the movie.
     */
    suspend fun getImages(
        movieId: Int,
        language: String? = null,
        includeImageLanguage: String? = null
    ): Images {
        val apiUrl = ApiUrl("movie", movieId, "images")
        apiUrl.addLanguage(language)
        apiUrl.addParam("include_image_language", includeImageLanguage)

        return request(apiUrl)
    }

    /**
     * Get the keywords for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @return The keywords of the movie.
     */
    suspend fun getKeywords(movieId: Int): KeywordResults {
        val apiUrl = ApiUrl("movie", movieId, "keywords")
        return request(apiUrl)
    }

    /**
     * Get a list of lists that the movie belongs to.
     *
     * @param movieId The TMDb id of the movie.
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @return The lists that the movie belongs to.
     */
    suspend fun getLists(
        movieId: Int,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<MovieList> {
        val apiUrl = ApiUrl("movie", movieId, "lists")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Get a list of recommended movies based on a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @return The recommended movies.
     */
    suspend fun getRecommendations(
        movieId: Int,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("movie", movieId, "recommendations")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Get the release dates and certifications for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @return The release dates of the movie.
     */
    suspend fun getReleaseDates(movieId: Int): ReleaseDateResults {
        val apiUrl = ApiUrl("movie", movieId, "release_dates")
        return request(apiUrl)
    }

    /**
     * Get the user reviews for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @return The reviews of the movie.
     */
    suspend fun getReviews(
        movieId: Int,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<Review> {
        val apiUrl = ApiUrl("movie", movieId, "reviews")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Get a list of similar movies.
     *
     * @param movieId The TMDb id of the movie.
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @return The similar movies.
     */
    suspend fun getSimilar(
        movieId: Int,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("movie", movieId, "similar")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Get the translations for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @return The translations of the movie.
     */
    suspend fun getTranslations(movieId: Int): Translations {
        val apiUrl = ApiUrl("movie", movieId, "translations")
        return request(apiUrl)
    }

    /**
     * Get the videos (trailers, teasers, clips, etc.) for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @param language The language to query the results in.
     * @return The videos of the movie.
     */
    suspend fun getVideos(
        movieId: Int,
        language: String? = null
    ): VideoResults {
        val apiUrl = ApiUrl("movie", movieId, "videos")
        apiUrl.addLanguage(language)

        return request(apiUrl)
    }

    /**
     * Get the watch providers for a movie.
     *
     * @param movieId The TMDb id of the movie.
     * @return The watch providers of the movie.
     */
    suspend fun getWatchProviders(movieId: Int): ProviderResults {
        val apiUrl = ApiUrl("movie", movieId, "watch/providers")
        return request(apiUrl)
    }

    /**
     * Get a list of movies that are currently in theaters.
     *
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @param region Filter results by region (ISO-3166-1 code).
     * @return The now playing movies.
     */
    suspend fun getNowPlaying(
        language: String? = null,
        page: Int? = null,
        region: String? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("movie", "now_playing")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)
        apiUrl.addParam("region", region)

        return request(apiUrl)
    }

    /**
     * Get a list of movies ordered by popularity.
     *
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @param region Filter results by region (ISO-3166-1 code).
     * @return The popular movies.
     */
    suspend fun getPopular(
        language: String? = null,
        page: Int? = null,
        region: String? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("movie", "popular")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)
        apiUrl.addParam("region", region)

        return request(apiUrl)
    }

    /**
     * Get a list of movies ordered by rating.
     *
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @param region Filter results by region (ISO-3166-1 code).
     * @return The top rated movies.
     */
    suspend fun getTopRated(
        language: String? = null,
        page: Int? = null,
        region: String? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("movie", "top_rated")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)
        apiUrl.addParam("region", region)

        return request(apiUrl)
    }

    /**
     * Get a list of movies that are being released soon.
     *
     * @param language The language to query the results in.
     * @param page The page of results to return (default: 1).
     * @param region Filter results by region (ISO-3166-1 code).
     * @return The upcoming movies.
     */
    suspend fun getUpcoming(
        language: String? = null,
        page: Int? = null,
        region: String? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("movie", "upcoming")
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)
        apiUrl.addParam("region", region)

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
