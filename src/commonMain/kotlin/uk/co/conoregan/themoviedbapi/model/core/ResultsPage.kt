package uk.co.conoregan.themoviedbapi.model.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Base class for paginated results from TMDb API.
 */
@Serializable
data class ResultsPage<T>(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<T>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int
)
