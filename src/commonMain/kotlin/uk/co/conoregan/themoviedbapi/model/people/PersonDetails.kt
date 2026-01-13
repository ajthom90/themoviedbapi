package uk.co.conoregan.themoviedbapi.model.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uk.co.conoregan.themoviedbapi.model.movies.MovieSummary
import uk.co.conoregan.themoviedbapi.model.tv.TvSeriesSummary

/**
 * Detailed person information.
 */
@Serializable
data class Person(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("also_known_as")
    val alsoKnownAs: List<String> = emptyList(),

    @SerialName("biography")
    val biography: String? = null,

    @SerialName("birthday")
    val birthday: String? = null,

    @SerialName("deathday")
    val deathday: String? = null,

    @SerialName("gender")
    val gender: Int? = null, // 0 = Not set, 1 = Female, 2 = Male, 3 = Non-binary

    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("imdb_id")
    val imdbId: String? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("place_of_birth")
    val placeOfBirth: String? = null,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("profile_path")
    val profilePath: String? = null,

    // Append to response fields
    @SerialName("combined_credits")
    val combinedCredits: CombinedCredits? = null,

    @SerialName("external_ids")
    val externalIds: PersonExternalIds? = null,

    @SerialName("images")
    val images: PersonImages? = null,

    @SerialName("movie_credits")
    val movieCredits: MovieCredits? = null,

    @SerialName("tv_credits")
    val tvCredits: TvCredits? = null,

    @SerialName("translations")
    val translations: PersonTranslations? = null
) {
    /**
     * Get gender as a readable string.
     */
    fun getGenderString(): String = when (gender) {
        1 -> "Female"
        2 -> "Male"
        3 -> "Non-binary"
        else -> "Not specified"
    }

    /**
     * Check if person is alive (no deathday).
     */
    val isAlive: Boolean
        get() = deathday == null
}

/**
 * Combined movie and TV credits for a person.
 */
@Serializable
data class CombinedCredits(
    @SerialName("cast")
    val cast: List<CombinedCast> = emptyList(),

    @SerialName("crew")
    val crew: List<CombinedCrew> = emptyList()
)

/**
 * Combined cast credit (can be movie or TV).
 */
@Serializable
data class CombinedCast(
    @SerialName("id")
    val id: Int,

    @SerialName("media_type")
    val mediaType: String, // "movie" or "tv"

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("character")
    val character: String? = null,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0,

    // Movie-specific fields
    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    // TV-specific fields
    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("episode_count")
    val episodeCount: Int? = null
)

/**
 * Combined crew credit (can be movie or TV).
 */
@Serializable
data class CombinedCrew(
    @SerialName("id")
    val id: Int,

    @SerialName("media_type")
    val mediaType: String, // "movie" or "tv"

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("department")
    val department: String,

    @SerialName("job")
    val job: String,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0,

    // Movie-specific fields
    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    // TV-specific fields
    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("episode_count")
    val episodeCount: Int? = null
)

/**
 * Movie credits for a person.
 */
@Serializable
data class MovieCredits(
    @SerialName("cast")
    val cast: List<MovieCastCredit> = emptyList(),

    @SerialName("crew")
    val crew: List<MovieCrewCredit> = emptyList()
)

/**
 * Movie cast credit.
 */
@Serializable
data class MovieCastCredit(
    @SerialName("id")
    val id: Int,

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("character")
    val character: String,

    @SerialName("order")
    val order: Int? = null,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0
)

/**
 * Movie crew credit.
 */
@Serializable
data class MovieCrewCredit(
    @SerialName("id")
    val id: Int,

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("department")
    val department: String,

    @SerialName("job")
    val job: String,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0
)

/**
 * TV credits for a person.
 */
@Serializable
data class TvCredits(
    @SerialName("cast")
    val cast: List<TvCastCredit> = emptyList(),

    @SerialName("crew")
    val crew: List<TvCrewCredit> = emptyList()
)

/**
 * TV cast credit.
 */
@Serializable
data class TvCastCredit(
    @SerialName("id")
    val id: Int,

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("character")
    val character: String,

    @SerialName("episode_count")
    val episodeCount: Int,

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

/**
 * TV crew credit.
 */
@Serializable
data class TvCrewCredit(
    @SerialName("id")
    val id: Int,

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("department")
    val department: String,

    @SerialName("job")
    val job: String,

    @SerialName("episode_count")
    val episodeCount: Int,

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

/**
 * External IDs for a person.
 */
@Serializable
data class PersonExternalIds(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("freebase_mid")
    val freebaseMid: String? = null,

    @SerialName("freebase_id")
    val freebaseId: String? = null,

    @SerialName("imdb_id")
    val imdbId: String? = null,

    @SerialName("tvrage_id")
    val tvrageId: Int? = null,

    @SerialName("wikidata_id")
    val wikidataId: String? = null,

    @SerialName("facebook_id")
    val facebookId: String? = null,

    @SerialName("instagram_id")
    val instagramId: String? = null,

    @SerialName("tiktok_id")
    val tiktokId: String? = null,

    @SerialName("twitter_id")
    val twitterId: String? = null,

    @SerialName("youtube_id")
    val youtubeId: String? = null
)

/**
 * Person images (profile photos).
 */
@Serializable
data class PersonImages(
    @SerialName("profiles")
    val profiles: List<ProfileImage> = emptyList()
)

@Serializable
data class ProfileImage(
    @SerialName("aspect_ratio")
    val aspectRatio: Double,

    @SerialName("file_path")
    val filePath: String,

    @SerialName("height")
    val height: Int,

    @SerialName("iso_639_1")
    val iso6391: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0,

    @SerialName("width")
    val width: Int
)

/**
 * Person translations.
 */
@Serializable
data class PersonTranslations(
    @SerialName("translations")
    val translations: List<PersonTranslation> = emptyList()
)

@Serializable
data class PersonTranslation(
    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("iso_639_1")
    val iso6391: String,

    @SerialName("name")
    val name: String,

    @SerialName("english_name")
    val englishName: String,

    @SerialName("data")
    val data: PersonTranslationData? = null
)

@Serializable
data class PersonTranslationData(
    @SerialName("biography")
    val biography: String? = null
)
