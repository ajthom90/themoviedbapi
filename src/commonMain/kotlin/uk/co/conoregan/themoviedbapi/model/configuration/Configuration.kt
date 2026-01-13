package uk.co.conoregan.themoviedbapi.model.configuration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API configuration details.
 */
@Serializable
data class Configuration(
    @SerialName("images")
    val images: ImageConfiguration,

    @SerialName("change_keys")
    val changeKeys: List<String> = emptyList()
)

/**
 * Image configuration for building image URLs.
 */
@Serializable
data class ImageConfiguration(
    @SerialName("base_url")
    val baseUrl: String,

    @SerialName("secure_base_url")
    val secureBaseUrl: String,

    @SerialName("backdrop_sizes")
    val backdropSizes: List<String>,

    @SerialName("logo_sizes")
    val logoSizes: List<String>,

    @SerialName("poster_sizes")
    val posterSizes: List<String>,

    @SerialName("profile_sizes")
    val profileSizes: List<String>,

    @SerialName("still_sizes")
    val stillSizes: List<String>
)

/**
 * Country information.
 */
@Serializable
data class Country(
    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("english_name")
    val englishName: String,

    @SerialName("native_name")
    val nativeName: String? = null
)

/**
 * Department with associated jobs.
 */
@Serializable
data class Department(
    @SerialName("department")
    val department: String,

    @SerialName("jobs")
    val jobs: List<String>
)

/**
 * Timezone information.
 */
@Serializable
data class Timezone(
    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("zones")
    val zones: List<String>
)
