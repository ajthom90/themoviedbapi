package uk.co.conoregan.themoviedbapi.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.client.TmdbSerializationException
import uk.co.conoregan.themoviedbapi.model.core.Genre
import uk.co.conoregan.themoviedbapi.util.ApiUrl

/**
 * TMDb API endpoints for genres.
 *
 * Get the official list of genres for movies and TV shows.
 *
 * See the [TMDb Genre API documentation](https://developer.themoviedb.org/reference/genre-movie-list) for more info.
 */
class TmdbGenres internal constructor(private val api: TmdbApi) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Serializable
    private data class GenresResponse(
        @SerialName("genres")
        val genres: List<Genre>
    )

    /**
     * Get the list of official genres for movies.
     *
     * @param language The language to query results in (default: en-US).
     * @return List of movie genres.
     *
     * Example:
     * ```kotlin
     * val movieGenres = tmdb.genres.getMovieGenres()
     * movieGenres.forEach { genre ->
     *     println("${genre.id}: ${genre.name}")
     * }
     * ```
     */
    suspend fun getMovieGenres(language: String? = null): List<Genre> {
        val apiUrl = ApiUrl("genre", "movie", "list")
        apiUrl.addLanguage(language)

        val response = api.httpClient.get(
            path = apiUrl.getPath(),
            parameters = apiUrl.getParameters()
        )

        return try {
            json.decodeFromString<GenresResponse>(response).genres
        } catch (e: Exception) {
            throw TmdbSerializationException("Failed to parse response: ${e.message}", e)
        }
    }

    /**
     * Get the list of official genres for TV shows.
     *
     * @param language The language to query results in (default: en-US).
     * @return List of TV show genres.
     *
     * Example:
     * ```kotlin
     * val tvGenres = tmdb.genres.getTvGenres()
     * ```
     */
    suspend fun getTvGenres(language: String? = null): List<Genre> {
        val apiUrl = ApiUrl("genre", "tv", "list")
        apiUrl.addLanguage(language)

        val response = api.httpClient.get(
            path = apiUrl.getPath(),
            parameters = apiUrl.getParameters()
        )

        return try {
            json.decodeFromString<GenresResponse>(response).genres
        } catch (e: Exception) {
            throw TmdbSerializationException("Failed to parse response: ${e.message}", e)
        }
    }
}
