package uk.co.conoregan.themoviedbapi.api

import kotlinx.serialization.json.Json
import uk.co.conoregan.themoviedbapi.client.TmdbSerializationException
import uk.co.conoregan.themoviedbapi.model.core.ResultsPage
import uk.co.conoregan.themoviedbapi.model.movies.MovieSummary
import uk.co.conoregan.themoviedbapi.model.people.PersonSummary
import uk.co.conoregan.themoviedbapi.model.search.MultiSearchResult
import uk.co.conoregan.themoviedbapi.model.tv.TvSeriesSummary
import uk.co.conoregan.themoviedbapi.util.ApiUrl

/**
 * TMDb API endpoints for trending content.
 *
 * Get trending movies, TV shows, and people.
 *
 * See the [TMDb Trending API documentation](https://developer.themoviedb.org/reference/trending-all) for more info.
 */
class TmdbTrending internal constructor(private val api: TmdbApi) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    /**
     * Time window for trending content.
     */
    enum class TimeWindow(val value: String) {
        DAY("day"),
        WEEK("week")
    }

    /**
     * Get all trending content (movies, TV shows, and people).
     *
     * @param timeWindow Time window for trending content (day or week).
     * @param language The language to query results in (default: en-US).
     * @param page The page of results to return (default: 1).
     * @return Paginated results containing mixed media types.
     *
     * Example:
     * ```kotlin
     * val trending = tmdb.trending.getAll(TimeWindow.DAY)
     * ```
     */
    suspend fun getAll(
        timeWindow: TimeWindow = TimeWindow.DAY,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<MultiSearchResult> {
        val apiUrl = ApiUrl("trending", "all", timeWindow.value)
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Get trending movies.
     *
     * @param timeWindow Time window for trending content (day or week).
     * @param language The language to query results in (default: en-US).
     * @param page The page of results to return (default: 1).
     * @return Paginated movie results.
     *
     * Example:
     * ```kotlin
     * val trendingMovies = tmdb.trending.getMovies(TimeWindow.WEEK)
     * ```
     */
    suspend fun getMovies(
        timeWindow: TimeWindow = TimeWindow.DAY,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<MovieSummary> {
        val apiUrl = ApiUrl("trending", "movie", timeWindow.value)
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Get trending people.
     *
     * @param timeWindow Time window for trending content (day or week).
     * @param language The language to query results in (default: en-US).
     * @param page The page of results to return (default: 1).
     * @return Paginated person results.
     *
     * Example:
     * ```kotlin
     * val trendingPeople = tmdb.trending.getPeople(TimeWindow.DAY)
     * ```
     */
    suspend fun getPeople(
        timeWindow: TimeWindow = TimeWindow.DAY,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<PersonSummary> {
        val apiUrl = ApiUrl("trending", "person", timeWindow.value)
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

        return request(apiUrl)
    }

    /**
     * Get trending TV shows.
     *
     * @param timeWindow Time window for trending content (day or week).
     * @param language The language to query results in (default: en-US).
     * @param page The page of results to return (default: 1).
     * @return Paginated TV series results.
     *
     * Example:
     * ```kotlin
     * val trendingTv = tmdb.trending.getTvShows(TimeWindow.WEEK)
     * ```
     */
    suspend fun getTvShows(
        timeWindow: TimeWindow = TimeWindow.DAY,
        language: String? = null,
        page: Int? = null
    ): ResultsPage<TvSeriesSummary> {
        val apiUrl = ApiUrl("trending", "tv", timeWindow.value)
        apiUrl.addLanguage(language)
        apiUrl.addPage(page)

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
