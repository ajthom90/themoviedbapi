package uk.co.conoregan.themoviedbapi.util

/**
 * Builder for constructing TMDb API URLs with query parameters.
 */
class ApiUrl(vararg pathSegments: Any) {
    private val path: String = pathSegments.joinToString("/")
    private val parameters = mutableMapOf<String, String?>()

    /**
     * Add a query parameter.
     */
    fun addParam(key: String, value: String?) {
        if (value != null) {
            parameters[key] = value
        }
    }

    /**
     * Add a query parameter.
     */
    fun addParam(key: String, value: Int?) {
        if (value != null) {
            parameters[key] = value.toString()
        }
    }

    /**
     * Add a query parameter.
     */
    fun addParam(key: String, value: Boolean?) {
        if (value != null) {
            parameters[key] = value.toString()
        }
    }

    /**
     * Add language parameter.
     */
    fun addLanguage(language: String?) {
        addParam("language", language)
    }

    /**
     * Add page parameter.
     */
    fun addPage(page: Int?) {
        addParam("page", page)
    }

    /**
     * Add append_to_response parameter.
     */
    fun addAppendToResponse(vararg values: String) {
        if (values.isNotEmpty()) {
            parameters["append_to_response"] = values.joinToString(",")
        }
    }

    /**
     * Get the path portion of the URL.
     */
    fun getPath(): String = path

    /**
     * Get the query parameters.
     */
    fun getParameters(): Map<String, String?> = parameters.toMap()
}
