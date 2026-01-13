package uk.co.conoregan.themoviedbapi.model.collections

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uk.co.conoregan.themoviedbapi.model.movies.MovieSummary

/**
 * Collection details.
 */
@Serializable
data class Collection(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("parts")
    val parts: List<MovieSummary> = emptyList()
)
