package uk.co.conoregan.themoviedbapi.model.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uk.co.conoregan.themoviedbapi.model.movies.MovieSummary
import uk.co.conoregan.themoviedbapi.model.tv.TvSeriesSummary

/**
 * Person information (used in search results).
 */
@Serializable
data class PersonSummary(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("gender")
    val gender: Int? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("profile_path")
    val profilePath: String? = null
)
