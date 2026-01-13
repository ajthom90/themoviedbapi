package uk.co.conoregan.themoviedbapi.api

import uk.co.conoregan.themoviedbapi.client.TmdbSerializationException
import uk.co.conoregan.themoviedbapi.model.core.ResultsPage
import uk.co.conoregan.themoviedbapi.model.movies.MovieSummary
import uk.co.conoregan.themoviedbapi.model.people.PersonSummary
import uk.co.conoregan.themoviedbapi.model.search.*
import uk.co.conoregan.themoviedbapi.model.tv.TvSeriesSummary
import uk.co.conoregan.themoviedbapi.util.ApiUrl
import kotlinx.serialization.json.Json

/**
 * TMDb API endpoints for searching.
 *
 * Search for movies, TV shows, people, collections, companies, and keywords.
 *
 * See the [TMDb Search API documentation](https://developer.themoviedb.org/reference/search-collection) for more info.
 */
class TmdbSearch internal constructor(private val api: TmdbApi) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Search for collections by their original, translated and alternative names.
     *
     * @param query The search query string.
     * @param language The language to query results in (default: en-US).
     * @param includeAdult Whether to include adult (18+) content in results.
     * @param page The page of results to return (default: 1).
     * @param region Filter results by region (ISO-3166-1 code), e.g., "US".
     * @return Paginated collection search results.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchCollections(
     *     query = "Lord of the Rings"
     * )
     * ```
     */
    suspend fun searchCollections(
        query: String,
        language: String? = null,
        includeAdult: Boolean? = null,
        page: Int? = null,
        region: String? = null
    ): ResultsPage<CollectionSearchResult> {
        val apiUrl = ApiUrl("search", "collection")
        apiUrl.addParam("query", query)
        apiUrl.addLanguage(language)
        apiUrl.addParam("include_adult", includeAdult)
        apiUrl.addPage(page)
        apiUrl.addParam("region", region)

        return request(apiUrl)
    }

    /**
     * Search for companies by their original and alternative names.
     *
     * @param query The search query string.
     * @param page The page of results to return (default: 1).
     * @return Paginated company search results.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchCompanies(query = "Marvel")
     * ```
     */
    suspend fun searchCompanies(
        query: String,
        page: Int? = null
    ): ResultsPage<CompanySearchResult> {
        val apiUrl = ApiUrl("search", "company")
        apiUrl.addParam("query", query)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Search for keywords by their name.
     *
     * @param query The search query string.
     * @param page The page of results to return (default: 1).
     * @return Paginated keyword search results.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchKeywords(query = "superhero")
     * ```
     */
    suspend fun searchKeywords(
        query: String,
        page: Int? = null
    ): ResultsPage<KeywordSearchResult> {
        val apiUrl = ApiUrl("search", "keyword")
        apiUrl.addParam("query", query)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Search for movies by their original, translated and alternative titles.
     *
     * @param query The search query string.
     * @param includeAdult Whether to include adult (18+) content in results.
     * @param language The language to query results in (default: en-US).
     * @param primaryReleaseYear Filter results by primary release year.
     * @param page The page of results to return (default: 1).
     * @param region Filter results by region (ISO-3166-1 code), e.g., "US".
     * @param year Filter results by any release year.
     * @return Paginated movie search results.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchMovies(
     *     query = "Fight Club",
     *     year = "1999"
     * )
     * ```
     */
    suspend fun searchMovies(
        query: String,
        includeAdult: Boolean? = null,
        language: String? = null,
        primaryReleaseYear: String? = null,
        page: Int? = null,
        region: String? = null,
        year: String? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("search", "movie")
        apiUrl.addParam("query", query)
        apiUrl.addParam("include_adult", includeAdult)
        apiUrl.addLanguage(language)
        apiUrl.addParam("primary_release_year", primaryReleaseYear)
        apiUrl.addPage(page)
        apiUrl.addParam("region", region)
        apiUrl.addParam("year", year)

        return request(apiUrl)
    }

    /**
     * Search for movies, TV shows, and people in a single request.
     *
     * Multi search is useful when you want to search across all media types at once.
     *
     * @param query The search query string.
     * @param includeAdult Whether to include adult (18+) content in results.
     * @param language The language to query results in (default: en-US).
     * @param page The page of results to return (default: 1).
     * @return Paginated multi-search results containing movies, TV shows, and people.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchMulti(query = "Tom Hanks")
     * results.results.forEach { result ->
     *     when (result.mediaType) {
     *         "movie" -> println("Movie: ${result.asMovie()?.title}")
     *         "tv" -> println("TV: ${result.asTvSeries()?.name}")
     *         "person" -> println("Person: ${result.asPerson()?.name}")
     *     }
     * }
     * ```
     */
    suspend fun searchMulti(
        query: String,
        includeAdult: Boolean? = null,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<MultiSearchResult> {
        val apiUrl = ApiUrl("search", "multi")
        apiUrl.addParam("query", query)
        apiUrl.addParam("include_adult", includeAdult)
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Search for people by their name and also-known-as names.
     *
     * @param query The search query string.
     * @param includeAdult Whether to include adult (18+) content in results.
     * @param language The language to query results in (default: en-US).
     * @param page The page of results to return (default: 1).
     * @return Paginated person search results.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchPeople(query = "Brad Pitt")
     * ```
     */
    suspend fun searchPeople(
        query: String,
        includeAdult: Boolean? = null,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<PersonSummary> {
        val apiUrl = ApiUrl("search", "person")
        apiUrl.addParam("query", query)
        apiUrl.addParam("include_adult", includeAdult)
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Search for TV shows by their original, translated and alternative names.
     *
     * @param query The search query string.
     * @param firstAirDateYear Filter results by first air date year.
     * @param includeAdult Whether to include adult (18+) content in results.
     * @param language The language to query results in (default: en-US).
     * @param page The page of results to return (default: 1).
     * @param year Filter results by first air date year (same as firstAirDateYear).
     * @return Paginated TV series search results.
     *
     * Example:
     * ```kotlin
     * val results = tmdb.search.searchTvShows(
     *     query = "Breaking Bad",
     *     year = "2008"
     * )
     * ```
     */
    suspend fun searchTvShows(
        query: String,
        firstAirDateYear: String? = null,
        includeAdult: Boolean? = null,
        language: String? = null,
        page: Int? = null,
        year: String? = null
    ): ResultsPage<TvSeriesSummary> {
        val apiUrl = ApiUrl("search", "tv")
        apiUrl.addParam("query", query)
        apiUrl.addParam("first_air_date_year", firstAirDateYear)
        apiUrl.addParam("include_adult", includeAdult)
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)
        apiUrl.addParam("year", year)

        return request(apiUrl)
    }

    /**
     * Make a request to the TMDb API and parse the response.
     */
    private suspend inline fun <reified T> request(apiUrl: ApiUrl): T {
        val response = api.httpClient.get(
            path = apiUrl.getPath(),
            parameters = apiUrl.getParameters()
        )

        return try {
            json.decodeFromString<T>(response)
        } catch (e: Exception) {
            throw TmdbSerializationException("Failed to parse response: ${e.message}", e)
        }
    }
}
