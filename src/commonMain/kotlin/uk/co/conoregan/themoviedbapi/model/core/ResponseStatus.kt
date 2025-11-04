package uk.co.conoregan.themoviedbapi.model.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Standard response status from TMDb API.
 */
@Serializable
data class ResponseStatus(
    @SerialName("status_code")
    val statusCode: Int,

    @SerialName("status_message")
    val statusMessage: String,

    @SerialName("success")
    val success: Boolean = false
)

/**
 * Response status for authentication operations.
 */
@Serializable
data class ResponseStatusAuthentication(
    @SerialName("success")
    val success: Boolean,

    @SerialName("expires_at")
    val expiresAt: String? = null,

    @SerialName("request_token")
    val requestToken: String? = null
)

/**
 * Response status for delete operations.
 */
@Serializable
data class ResponseStatusDelete(
    @SerialName("status_code")
    val statusCode: Int,

    @SerialName("status_message")
    val statusMessage: String,

    @SerialName("success")
    val success: Boolean
)
