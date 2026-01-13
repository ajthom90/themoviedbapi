package uk.co.conoregan.themoviedbapi.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * HTTP client for making requests to the TMDb API.
 *
 * @property apiKey The TMDb API key (Read Access Token).
 * @property baseUrl The base URL for TMDb API (default: https://api.themoviedb.org/3).
 * @property enableLogging Enable HTTP request/response logging.
 */
class TmdbHttpClient(
    private val apiKey: String,
    private val baseUrl: String = "https://api.themoviedb.org/3",
    enableLogging: Boolean = false
) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        encodeDefaults = true
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(this@TmdbHttpClient.json)
        }

        if (enableLogging) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60000
            connectTimeoutMillis = 60000
        }

        defaultRequest {
            header("Authorization", "Bearer $apiKey")
            header("Accept", "application/json")
            contentType(ContentType.Application.Json)
        }
    }

    /**
     * Make a GET request to the TMDb API.
     *
     * @param path The API path (e.g., "movie/550").
     * @param parameters Query parameters for the request.
     * @return The response body as a string.
     */
    suspend fun get(
        path: String,
        parameters: Map<String, String?> = emptyMap()
    ): String {
        return request(RequestMethod.GET, path, parameters)
    }

    /**
     * Make a POST request to the TMDb API.
     *
     * @param path The API path.
     * @param parameters Query parameters for the request.
     * @param body The request body as a string.
     * @return The response body as a string.
     */
    suspend fun post(
        path: String,
        parameters: Map<String, String?> = emptyMap(),
        body: String? = null
    ): String {
        return request(RequestMethod.POST, path, parameters, body)
    }

    /**
     * Make a DELETE request to the TMDb API.
     *
     * @param path The API path.
     * @param parameters Query parameters for the request.
     * @return The response body as a string.
     */
    suspend fun delete(
        path: String,
        parameters: Map<String, String?> = emptyMap()
    ): String {
        return request(RequestMethod.DELETE, path, parameters)
    }

    /**
     * Make a request to the TMDb API.
     *
     * @param method The HTTP method.
     * @param path The API path.
     * @param parameters Query parameters.
     * @param body Optional request body.
     * @return The response body as a string.
     */
    private suspend fun request(
        method: RequestMethod,
        path: String,
        parameters: Map<String, String?> = emptyMap(),
        body: String? = null
    ): String {
        try {
            val url = "$baseUrl/$path"

            val response: HttpResponse = httpClient.request(url) {
                this.method = when (method) {
                    RequestMethod.GET -> HttpMethod.Get
                    RequestMethod.POST -> HttpMethod.Post
                    RequestMethod.DELETE -> HttpMethod.Delete
                }

                // Add query parameters, filtering out null values
                parameters.filterValues { it != null }.forEach { (key, value) ->
                    parameter(key, value)
                }

                // Add body for POST requests
                if (method == RequestMethod.POST && body != null) {
                    setBody(body)
                }
            }

            // Check if response is successful
            if (!response.status.isSuccess()) {
                // Try to parse error response
                val errorBody = response.bodyAsText()
                throw TmdbResponseException(
                    statusCode = response.status.value,
                    statusMessage = errorBody.ifEmpty { response.status.description }
                )
            }

            return response.bodyAsText()
        } catch (e: TmdbException) {
            throw e
        } catch (e: Exception) {
            throw TmdbNetworkException("Network request failed: ${e.message}", e)
        }
    }

    /**
     * Close the HTTP client and release resources.
     */
    fun close() {
        httpClient.close()
    }
}
