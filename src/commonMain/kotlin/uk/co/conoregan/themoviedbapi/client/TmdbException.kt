package uk.co.conoregan.themoviedbapi.client

/**
 * Base exception for all TMDb API errors.
 */
open class TmdbException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)

/**
 * Exception thrown when TMDb API returns an error response.
 */
class TmdbResponseException(
    val statusCode: Int,
    val statusMessage: String,
    val success: Boolean = false
) : TmdbException("TMDb API Error [$statusCode]: $statusMessage")

/**
 * Exception thrown when network request fails.
 */
class TmdbNetworkException(
    message: String,
    cause: Throwable? = null
) : TmdbException(message, cause)

/**
 * Exception thrown when JSON parsing fails.
 */
class TmdbSerializationException(
    message: String,
    cause: Throwable? = null
) : TmdbException(message, cause)
