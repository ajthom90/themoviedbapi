package uk.co.conoregan.themoviedbapi.model.movies

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uk.co.conoregan.themoviedbapi.model.core.*

/**
 * Detailed movie information from TMDb API.
 */
@Serializable
data class Movie(
    @SerialName("id")
    val id: Int,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,

    @SerialName("budget")
    val budget: Int = 0,

    @SerialName("genres")
    val genres: List<Genre> = emptyList(),

    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("imdb_id")
    val imdbId: String? = null,

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

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("revenue")
    val revenue: Long = 0,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<Language> = emptyList(),

    @SerialName("status")
    val status: String? = null,

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0,

    // Append to response fields
    @SerialName("account_states")
    val accountStates: AccountStates? = null,

    @SerialName("alternative_titles")
    val alternativeTitles: AlternativeTitles? = null,

    @SerialName("credits")
    val credits: Credits? = null,

    @SerialName("external_ids")
    val externalIds: ExternalIds? = null,

    @SerialName("images")
    val images: Images? = null,

    @SerialName("keywords")
    val keywords: KeywordResults? = null,

    @SerialName("lists")
    val lists: ResultsPage<MovieList>? = null,

    @SerialName("recommendations")
    val recommendations: ResultsPage<MovieSummary>? = null,

    @SerialName("release_dates")
    val releaseDates: ReleaseDateResults? = null,

    @SerialName("reviews")
    val reviews: ResultsPage<Review>? = null,

    @SerialName("similar")
    val similar: ResultsPage<MovieSummary>? = null,

    @SerialName("translations")
    val translations: Translations? = null,

    @SerialName("videos")
    val videos: VideoResults? = null,

    @SerialName("watch/providers")
    val watchProviders: ProviderResults? = null
)

/**
 * Simplified movie information (used in lists/search results).
 */
@Serializable
data class MovieSummary(
    @SerialName("id")
    val id: Int,

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
 * Collection that a movie belongs to.
 */
@Serializable
data class BelongsToCollection(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null
)

/**
 * Alternative titles for a movie.
 */
@Serializable
data class AlternativeTitles(
    @SerialName("titles")
    val titles: List<AlternativeTitle> = emptyList()
)

@Serializable
data class AlternativeTitle(
    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("title")
    val title: String,

    @SerialName("type")
    val type: String? = null
)

/**
 * Movie credits (cast and crew).
 */
@Serializable
data class Credits(
    @SerialName("cast")
    val cast: List<CastMember> = emptyList(),

    @SerialName("crew")
    val crew: List<CrewMember> = emptyList()
)

@Serializable
data class CastMember(
    @SerialName("id")
    val id: Int,

    @SerialName("cast_id")
    val castId: Int? = null,

    @SerialName("character")
    val character: String,

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("gender")
    val gender: Int? = null,

    @SerialName("name")
    val name: String,

    @SerialName("order")
    val order: Int,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("original_name")
    val originalName: String,

    @SerialName("popularity")
    val popularity: Double = 0.0
)

@Serializable
data class CrewMember(
    @SerialName("id")
    val id: Int,

    @SerialName("credit_id")
    val creditId: String,

    @SerialName("department")
    val department: String,

    @SerialName("gender")
    val gender: Int? = null,

    @SerialName("job")
    val job: String,

    @SerialName("name")
    val name: String,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("original_name")
    val originalName: String,

    @SerialName("popularity")
    val popularity: Double = 0.0
)

/**
 * External IDs for a movie.
 */
@Serializable
data class ExternalIds(
    @SerialName("imdb_id")
    val imdbId: String? = null,

    @SerialName("facebook_id")
    val facebookId: String? = null,

    @SerialName("instagram_id")
    val instagramId: String? = null,

    @SerialName("twitter_id")
    val twitterId: String? = null,

    @SerialName("wikidata_id")
    val wikidataId: String? = null
)

/**
 * Images for a movie.
 */
@Serializable
data class Images(
    @SerialName("backdrops")
    val backdrops: List<Image> = emptyList(),

    @SerialName("logos")
    val logos: List<Image> = emptyList(),

    @SerialName("posters")
    val posters: List<Image> = emptyList()
)

@Serializable
data class Image(
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
 * Keywords for a movie.
 */
@Serializable
data class KeywordResults(
    @SerialName("keywords")
    val keywords: List<Keyword> = emptyList()
)

@Serializable
data class Keyword(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)

/**
 * Movie list information.
 */
@Serializable
data class MovieList(
    @SerialName("id")
    val id: String,

    @SerialName("description")
    val description: String? = null,

    @SerialName("favorite_count")
    val favoriteCount: Int = 0,

    @SerialName("item_count")
    val itemCount: Int = 0,

    @SerialName("iso_639_1")
    val iso6391: String,

    @SerialName("list_type")
    val listType: String,

    @SerialName("name")
    val name: String,

    @SerialName("poster_path")
    val posterPath: String? = null
)

/**
 * Release date information.
 */
@Serializable
data class ReleaseDateResults(
    @SerialName("results")
    val results: List<ReleaseInfo> = emptyList()
)

@Serializable
data class ReleaseInfo(
    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("release_dates")
    val releaseDates: List<ReleaseDate> = emptyList()
)

@Serializable
data class ReleaseDate(
    @SerialName("certification")
    val certification: String? = null,

    @SerialName("iso_639_1")
    val iso6391: String? = null,

    @SerialName("note")
    val note: String? = null,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("type")
    val type: Int
)

/**
 * Review information.
 */
@Serializable
data class Review(
    @SerialName("id")
    val id: String,

    @SerialName("author")
    val author: String,

    @SerialName("author_details")
    val authorDetails: AuthorDetails? = null,

    @SerialName("content")
    val content: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("url")
    val url: String
)

@Serializable
data class AuthorDetails(
    @SerialName("name")
    val name: String? = null,

    @SerialName("username")
    val username: String,

    @SerialName("avatar_path")
    val avatarPath: String? = null,

    @SerialName("rating")
    val rating: Double? = null
)

/**
 * Translations for a movie.
 */
@Serializable
data class Translations(
    @SerialName("translations")
    val translations: List<Translation> = emptyList()
)

@Serializable
data class Translation(
    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("iso_639_1")
    val iso6391: String,

    @SerialName("name")
    val name: String,

    @SerialName("english_name")
    val englishName: String,

    @SerialName("data")
    val data: TranslationData? = null
)

@Serializable
data class TranslationData(
    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("title")
    val title: String? = null
)

/**
 * Videos (trailers, teasers, etc.) for a movie.
 */
@Serializable
data class VideoResults(
    @SerialName("results")
    val results: List<Video> = emptyList()
)

@Serializable
data class Video(
    @SerialName("id")
    val id: String,

    @SerialName("iso_639_1")
    val iso6391: String,

    @SerialName("iso_3166_1")
    val iso31661: String,

    @SerialName("key")
    val key: String,

    @SerialName("name")
    val name: String,

    @SerialName("official")
    val official: Boolean = false,

    @SerialName("published_at")
    val publishedAt: String? = null,

    @SerialName("site")
    val site: String,

    @SerialName("size")
    val size: Int,

    @SerialName("type")
    val type: String
)

/**
 * Watch provider information.
 */
@Serializable
data class ProviderResults(
    @SerialName("results")
    val results: Map<String, WatchProviders> = emptyMap()
)

@Serializable
data class WatchProviders(
    @SerialName("link")
    val link: String? = null,

    @SerialName("buy")
    val buy: List<Provider> = emptyList(),

    @SerialName("flatrate")
    val flatrate: List<Provider> = emptyList(),

    @SerialName("rent")
    val rent: List<Provider> = emptyList()
)

@Serializable
data class Provider(
    @SerialName("logo_path")
    val logoPath: String,

    @SerialName("provider_id")
    val providerId: Int,

    @SerialName("provider_name")
    val providerName: String,

    @SerialName("display_priority")
    val displayPriority: Int
)
