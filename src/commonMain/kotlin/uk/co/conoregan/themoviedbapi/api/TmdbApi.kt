package uk.co.conoregan.themoviedbapi.api

import uk.co.conoregan.themoviedbapi.client.TmdbHttpClient

/**
 * Main entry point for The Movie Database (TMDb) API v3.
 *
 * This is a Kotlin Multiplatform SDK that provides access to all TMDb API endpoints.
 *
 * Usage:
 * ```kotlin
 * val tmdb = TmdbApi(apiKey = "your-api-read-access-token")
 * val movie = tmdb.movies.getDetails(movieId = 550)
 * ```
 *
 * @property apiKey Your TMDb API Read Access Token. Get yours at https://www.themoviedb.org/settings/api
 * @property baseUrl The base URL for TMDb API (default: https://api.themoviedb.org/3)
 * @property enableLogging Enable HTTP request/response logging for debugging
 */
class TmdbApi(
    apiKey: String,
    baseUrl: String = "https://api.themoviedb.org/3",
    enableLogging: Boolean = false
) {
    internal val httpClient = TmdbHttpClient(
        apiKey = apiKey,
        baseUrl = baseUrl,
        enableLogging = enableLogging
    )

    /**
     * Access movie-related API endpoints.
     *
     * Example:
     * ```kotlin
     * val movie = tmdb.movies.getDetails(550)
     * ```
     */
    val movies: TmdbMovies by lazy { TmdbMovies(this) }

    /**
     * Access search API endpoints.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchMovies("Fight Club")
     * ```
     */
    val search: TmdbSearch by lazy { TmdbSearch(this) }

    /**
     * Access trending content API endpoints.
     *
     * Example:
     * ```kotlin
     * val trendingMovies = tmdb.trending.getTrendingMovies("day")
     * ```
     */
    val trending: TmdbTrending by lazy { TmdbTrending(this) }

    /**
     * Access configuration API endpoints.
     *
     * Example:
     * ```kotlin
     * val config = tmdb.configuration.getApiConfiguration()
     * ```
     */
    val configuration: TmdbConfiguration by lazy { TmdbConfiguration(this) }

    /**
     * Access genres API endpoints.
     *
     * Example:
     * ```kotlin
     * val movieGenres = tmdb.genres.getMovieGenres()
     * ```
     */
    val genres: TmdbGenres by lazy { TmdbGenres(this) }

    /**
     * Access collections API endpoints.
     *
     * Example:
     * ```kotlin
     * val collection = tmdb.collections.getDetails(119)
     * ```
     */
    val collections: TmdbCollections by lazy { TmdbCollections(this) }

    /**
     * Access people API endpoints.
     *
     * Example:
     * ```kotlin
     * val person = tmdb.people.getDetails(287) // Brad Pitt
     * val credits = tmdb.people.getCombinedCredits(287)
     * ```
     */
    val people: TmdbPeople by lazy { TmdbPeople(this) }

    /**
     * Close the API client and release resources.
     * Call this when you're done using the API.
     */
    fun close() {
        httpClient.close()
    }
}
