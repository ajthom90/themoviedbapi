package uk.co.conoregan.themoviedbapi.model.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uk.co.conoregan.themoviedbapi.model.movies.MovieSummary
import uk.co.conoregan.themoviedbapi.model.people.PersonSummary
import uk.co.conoregan.themoviedbapi.model.tv.TvSeriesSummary

/**
 * Multi-search result that can be a movie, TV series, or person.
 */
@Serializable
data class MultiSearchResult(
    @SerialName("media_type")
    val mediaType: String,

    // Movie fields
    @SerialName("id")
    val id: Int? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    // Movie-specific
    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    // TV-specific
    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("name")
    val name: String? = null,

    // Person-specific
    @SerialName("gender")
    val gender: Int? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("profile_path")
    val profilePath: String? = null
) {
    /**
     * Convert to specific type based on media_type.
     */
    fun asMovie(): MovieSummary? {
        if (mediaType != "movie") return null
        return MovieSummary(
            id = id ?: return null,
            adult = adult ?: false,
            backdropPath = backdropPath,
            genreIds = genreIds ?: emptyList(),
            originalLanguage = originalLanguage ?: "",
            originalTitle = originalTitle ?: "",
            overview = overview,
            popularity = popularity ?: 0.0,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title ?: "",
            video = video ?: false,
            voteAverage = voteAverage ?: 0.0,
            voteCount = voteCount ?: 0
        )
    }

    fun asTvSeries(): TvSeriesSummary? {
        if (mediaType != "tv") return null
        return TvSeriesSummary(
            id = id ?: return null,
            adult = adult ?: false,
            backdropPath = backdropPath,
            genreIds = genreIds ?: emptyList(),
            originCountry = originCountry ?: emptyList(),
            originalLanguage = originalLanguage ?: "",
            originalName = originalName ?: "",
            overview = overview,
            popularity = popularity ?: 0.0,
            posterPath = posterPath,
            firstAirDate = firstAirDate,
            name = name ?: "",
            voteAverage = voteAverage ?: 0.0,
            voteCount = voteCount ?: 0
        )
    }

    fun asPerson(): PersonSummary? {
        if (mediaType != "person") return null
        return PersonSummary(
            id = id ?: return null,
            name = name ?: "",
            adult = adult ?: false,
            gender = gender,
            knownForDepartment = knownForDepartment,
            originalName = originalName,
            popularity = popularity ?: 0.0,
            profilePath = profilePath
        )
    }
}

/**
 * Collection search result.
 */
@Serializable
data class CollectionSearchResult(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("overview")
    val overview: String? = null
)

/**
 * Company search result.
 */
@Serializable
data class CompanySearchResult(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("logo_path")
    val logoPath: String? = null,

    @SerialName("origin_country")
    val originCountry: String? = null
)

/**
 * Keyword search result.
 */
@Serializable
data class KeywordSearchResult(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)
