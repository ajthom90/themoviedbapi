package uk.co.conoregan.themoviedbapi.model.tv

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TV Series summary information (used in lists/search results).
 */
@Serializable
data class TvSeriesSummary(
    @SerialName("id")
    val id: Int,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_name")
    val originalName: String,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0
)
