package uk.co.conoregan.themoviedbapi.api

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.client.TmdbSerializationException
import uk.co.conoregan.themoviedbapi.model.collections.Collection
import uk.co.conoregan.themoviedbapi.util.ApiUrl

/**
 * TMDb API endpoints for collections.
 *
 * Get collection details including all movies in the collection.
 *
 * See the [TMDb Collection API documentation](https://developer.themoviedb.org/reference/collection-details) for more info.
 */
class TmdbCollections internal constructor(private val api: TmdbApi) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Get collection details by ID.
     *
     * @param collectionId The TMDb ID of the collection.
     * @param language The language to query results in (default: en-US).
     * @return The collection details.
     *
     * Example:
     * ```kotlin
     * // Get "The Lord of the Rings" collection
     * val collection = tmdb.collections.getDetails(119)
     * println("${collection.name}: ${collection.parts.size} movies")
     * collection.parts.forEach { movie ->
     *     println("  - ${movie.title}")
     * }
     * ```
     */
    suspend fun getDetails(
        collectionId: Int,
        language: String? = null
    ): Collection {
        val apiUrl = ApiUrl("collection", collectionId)
        apiUrl.addLanguage(language)

        val response = api.httpClient.get(
            path = apiUrl.getPath(),
            parameters = apiUrl.getParameters()
        )

        return try {
            json.decodeFromString<Collection>(response)
        } catch (e: Exception) {
            throw TmdbSerializationException("Failed to parse response: ${e.message}", e)
        }
    }
}
