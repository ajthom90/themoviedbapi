package uk.co.conoregan.themoviedbapi.model.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Genre information.
 */
@Serializable
data class Genre(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

/**
 * Production company information.
 */
@Serializable
data class ProductionCompany(
    @SerialName("id")
    val id: Int,

    @SerialName("logo_path")
    val logoPath: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("origin_country")
    val originCountry: String? = null
)

/**
 * Production country information.
 */
@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("name")
    val name: String
)

/**
 * Spoken language information.
 */
@Serializable
data class Language(
    @SerialName("english_name")
    val englishName: String? = null,

    @SerialName("iso_639_1")
    val iso6391: String,

    @SerialName("name")
    val name: String
)

/**
 * Account states for movies/TV shows.
 */
@Serializable
data class AccountStates(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("favorite")
    val favorite: Boolean = false,

    @SerialName("rated")
    val rated: RatedValue? = null,

    @SerialName("watchlist")
    val watchlist: Boolean = false
)

/**
 * Rated value wrapper.
 */
@Serializable
data class RatedValue(
    @SerialName("value")
    val value: Double
)
