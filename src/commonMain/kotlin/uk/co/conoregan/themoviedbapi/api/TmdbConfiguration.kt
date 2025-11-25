package uk.co.conoregan.themoviedbapi.api

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.client.TmdbSerializationException
import uk.co.conoregan.themoviedbapi.model.configuration.*
import uk.co.conoregan.themoviedbapi.model.core.Language
import uk.co.conoregan.themoviedbapi.util.ApiUrl

/**
 * TMDb API endpoints for configuration and system information.
 *
 * Get API configuration, countries, languages, timezones, and job departments.
 *
 * See the [TMDb Configuration API documentation](https://developer.themoviedb.org/reference/configuration-details) for more info.
 */
class TmdbConfiguration internal constructor(private val api: TmdbApi) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Get the API configuration details.
     *
     * This includes the image base URLs and available sizes for building image URLs.
     *
     * @return The API configuration.
     *
     * Example:
     * ```kotlin
     * val config = tmdb.configuration.getApiConfiguration()
     * val imageBaseUrl = config.images.secureBaseUrl
     * val posterSizes = config.images.posterSizes
     * // Build image URL: "$imageBaseUrl/w500/poster_path.jpg"
     * ```
     */
    suspend fun getApiConfiguration(): Configuration {
        val apiUrl = ApiUrl("configuration")
        return request(apiUrl)
    }

    /**
     * Get the list of countries (ISO 3166-1 tags) used throughout TMDb.
     *
     * @param language The language to query results in (default: en-US).
     * @return List of countries.
     *
     * Example:
     * ```kotlin
     * val countries = tmdb.configuration.getCountries()
     * ```
     */
    suspend fun getCountries(language: String? = null): List<Country> {
        val apiUrl = ApiUrl("configuration", "countries")
        apiUrl.addLanguage(language)

        return request(apiUrl)
    }

    /**
     * Get the list of departments and jobs used on TMDb.
     *
     * @return List of departments with their associated jobs.
     *
     * Example:
     * ```kotlin
     * val jobs = tmdb.configuration.getJobs()
     * jobs.forEach { department ->
     *     println("${department.department}: ${department.jobs.joinToString()}")
     * }
     * ```
     */
    suspend fun getJobs(): List<Department> {
        val apiUrl = ApiUrl("configuration", "jobs")
        return request(apiUrl)
    }

    /**
     * Get the list of languages (ISO 639-1 tags) used throughout TMDb.
     *
     * @return List of languages.
     *
     * Example:
     * ```kotlin
     * val languages = tmdb.configuration.getLanguages()
     * ```
     */
    suspend fun getLanguages(): List<Language> {
        val apiUrl = ApiUrl("configuration", "languages")
        return request(apiUrl)
    }

    /**
     * Get the list of primary translations used on TMDb.
     *
     * @return List of language codes.
     *
     * Example:
     * ```kotlin
     * val primaryTranslations = tmdb.configuration.getPrimaryTranslations()
     * ```
     */
    suspend fun getPrimaryTranslations(): List<String> {
        val apiUrl = ApiUrl("configuration", "primary_translations")
        return request(apiUrl)
    }

    /**
     * Get the list of timezones used throughout TMDb.
     *
     * @return List of timezones.
     *
     * Example:
     * ```kotlin
     * val timezones = tmdb.configuration.getTimezones()
     * ```
     */
    suspend fun getTimezones(): List<Timezone> {
        val apiUrl = ApiUrl("configuration", "timezones")
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
