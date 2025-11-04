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
     * Close the API client and release resources.
     * Call this when you're done using the API.
     */
    fun close() {
        httpClient.close()
    }
}
