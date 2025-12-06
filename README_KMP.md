# TheMovieDB API - Kotlin Multiplatform SDK

[![Download](https://img.shields.io/github/v/release/c-eg/themoviedbapi)](https://github.com/c-eg/themoviedbapi/releases)
[![BSD 2 License](http://img.shields.io/badge/license-BSD_2_Clause-green.svg)](https://opensource.org/licenses/BSD-2-Clause)

A **Kotlin Multiplatform** SDK for [The Movie Database (TMDb)](https://www.themoviedb.org/) [API v3](https://developer.themoviedb.org/docs/getting-started).

## 🚀 Kotlin Multiplatform Migration (v3.0.0)

This is a complete rewrite of the library as a Kotlin Multiplatform project, supporting:

### Supported Platforms
- ✅ **JVM** (Java 11+)
- ✅ **JavaScript** (Browser & Node.js)
- ✅ **iOS** (arm64, x64, simulator)
- ✅ **macOS** (arm64, x64)
- ✅ **watchOS** (arm32, arm64, x64, simulator)
- ✅ **tvOS** (arm64, x64, simulator)
- ✅ **Linux** (x64, arm64)
- ✅ **Windows** (mingw-x64)

### Key Improvements

#### 1. **Pure Kotlin**
- No more Java dependencies
- Idiomatic Kotlin APIs with coroutines
- Null safety built-in

#### 2. **Modern HTTP Client**
- Powered by [Ktor Client](https://ktor.io/docs/client.html) for multiplatform HTTP
- Replaces Java's HttpClient with platform-agnostic implementation
- Supports all platforms natively

#### 3. **kotlinx.serialization**
- Replaces Jackson with [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
- Faster, more efficient JSON parsing
- Multiplatform support

#### 4. **No Lombok**
- Uses Kotlin data classes instead
- Cleaner, more maintainable code
- Better IDE support

#### 5. **Suspending Functions**
- All API calls are now `suspend` functions
- Use with Kotlin coroutines for async/await pattern
- Better resource management

## 📦 Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("uk.co.conoregan:themoviedbapi:3.0.0")
}
```

### Maven

```xml
<dependency>
    <groupId>uk.co.conoregan</groupId>
    <artifactId>themoviedbapi</artifactId>
    <version>3.0.0</version>
</dependency>
```

## 🎯 Usage

### Getting Started

```kotlin
import uk.co.conoregan.themoviedbapi.api.TmdbApi
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // Initialize the API with your Read Access Token
    val tmdb = TmdbApi(apiKey = "your-api-read-access-token")

    // Get movie details
    val movie = tmdb.movies.getDetails(movieId = 550) // Fight Club
    println("${movie.title} (${movie.releaseDate})")
    println("Rating: ${movie.voteAverage}/10")

    // Don't forget to close when done
    tmdb.close()
}
```

### Using `use` for Automatic Resource Management

```kotlin
TmdbApi("your-api-key").use { tmdb ->
    val movie = tmdb.movies.getDetails(550)
    println(movie.title)
}
// Automatically closed
```

### With Append to Response

Reduce API calls by appending additional data to your request:

```kotlin
val movie = tmdb.movies.getDetails(
    movieId = 550,
    appendToResponse = arrayOf("credits", "videos", "images", "similar")
)

// Now you can access appended data without additional API calls
val cast = movie.credits?.cast ?: emptyList()
val videos = movie.videos?.results ?: emptyList()
val similarMovies = movie.similar?.results ?: emptyList()
```

### Popular Movies

```kotlin
val popularMovies = tmdb.movies.getPopular(
    language = "en-US",
    page = 1,
    region = "US"
)

popularMovies.results.forEach { movie ->
    println("${movie.title} - ${movie.popularity}")
}
```

### Searching Content

```kotlin
// Search for movies
val movieResults = tmdb.search.searchMovies(
    query = "Fight Club",
    year = "1999"
)

// Search for TV shows
val tvResults = tmdb.search.searchTvShows(query = "Breaking Bad")

// Search for people
val peopleResults = tmdb.search.searchPeople(query = "Brad Pitt")

// Multi-search (movies, TV, people)
val multiResults = tmdb.search.searchMulti(query = "Inception")
multiResults.results.forEach { result ->
    when (result.mediaType) {
        "movie" -> println("Movie: ${result.asMovie()?.title}")
        "tv" -> println("TV: ${result.asTvSeries()?.name}")
        "person" -> println("Person: ${result.asPerson()?.name}")
    }
}
```

### Trending Content

```kotlin
import uk.co.conoregan.themoviedbapi.api.TmdbTrending.TimeWindow

// Get trending movies this week
val trendingMovies = tmdb.trending.getMovies(TimeWindow.WEEK)

// Get trending TV shows today
val trendingTv = tmdb.trending.getTvShows(TimeWindow.DAY)

// Get trending people
val trendingPeople = tmdb.trending.getPeople(TimeWindow.DAY)

// Get all trending content (mixed)
val allTrending = tmdb.trending.getAll(TimeWindow.DAY)
```

### Configuration & Metadata

```kotlin
// Get API configuration for building image URLs
val config = tmdb.configuration.getApiConfiguration()
val imageBaseUrl = config.images.secureBaseUrl
val posterSize = config.images.posterSizes.first()
// Build image URL: "$imageBaseUrl/$posterSize/poster_path.jpg"

// Get available genres
val movieGenres = tmdb.genres.getMovieGenres()
val tvGenres = tmdb.genres.getTvGenres()

// Get languages
val languages = tmdb.configuration.getLanguages()

// Get countries
val countries = tmdb.configuration.getCountries()
```

### Collections

```kotlin
// Get collection details (e.g., "The Lord of the Rings" collection)
val collection = tmdb.collections.getDetails(119)
println("${collection.name}: ${collection.parts.size} movies")
collection.parts.forEach { movie ->
    println("  - ${movie.title} (${movie.releaseDate})")
}
```

### People & Credits

```kotlin
// Get person details
val person = tmdb.people.getDetails(287) // Brad Pitt
println("${person.name} - ${person.knownForDepartment}")
println("Born: ${person.birthday} in ${person.placeOfBirth}")
println("Biography: ${person.biography?.take(200)}...")

// Get combined credits (movies + TV)
val credits = tmdb.people.getCombinedCredits(287)
val movies = credits.cast.filter { it.mediaType == "movie" }
val tvShows = credits.cast.filter { it.mediaType == "tv" }
println("Movies: ${movies.size}, TV Shows: ${tvShows.size}")

// Get movie credits only
val movieCredits = tmdb.people.getMovieCredits(287)
movieCredits.cast.forEach { movie ->
    println("${movie.title} (${movie.releaseDate}) as ${movie.character}")
}

// Get TV credits only
val tvCredits = tmdb.people.getTvCredits(287)
tvCredits.cast.forEach { show ->
    println("${show.name} (${show.episodeCount} episodes) as ${show.character}")
}

// Get external IDs (social media)
val externalIds = tmdb.people.getExternalIds(287)
println("IMDB: ${externalIds.imdbId}")
println("Instagram: @${externalIds.instagramId}")
```

### Movie Lists with Multiple Endpoints

```kotlin
// Get now playing
val nowPlaying = tmdb.movies.getNowPlaying(region = "US")

// Get top rated
val topRated = tmdb.movies.getTopRated()

// Get upcoming
val upcoming = tmdb.movies.getUpcoming()

// Get recommendations based on a movie
val recommendations = tmdb.movies.getRecommendations(movieId = 550)
```

## 🔑 API Key

To use this SDK, you need a TMDb API **Read Access Token** (not the API key):

1. Create an account at [themoviedb.org](https://www.themoviedb.org/)
2. Go to [API Settings](https://www.themoviedb.org/settings/api)
3. Use the **API Read Access Token** (Bearer token), not the API Key (v3 auth)

## 🏗️ Architecture

### Project Structure

```
src/
├── commonMain/kotlin/uk/co/conoregan/themoviedbapi/
│   ├── api/           # API endpoint classes
│   │   ├── TmdbApi.kt
│   │   └── TmdbMovies.kt
│   ├── client/        # HTTP client & exceptions
│   │   ├── TmdbHttpClient.kt
│   │   └── TmdbException.kt
│   ├── model/         # Data models
│   │   ├── core/      # Common models
│   │   └── movies/    # Movie-specific models
│   └── util/          # Utilities
│       └── ApiUrl.kt
├── commonTest/        # Common tests
├── jvmMain/          # JVM-specific code
├── jsMain/           # JS-specific code
└── nativeMain/       # Native-specific code (iOS, macOS, etc.)
```

### HTTP Client

The SDK uses Ktor Client with different engines per platform:
- **JVM**: OkHttp
- **JS**: Fetch API
- **Apple platforms**: Darwin (NSURLSession)
- **Linux/Windows**: cURL

## 🧪 Testing

```bash
# Run all tests
./gradlew allTests

# Run JVM tests only
./gradlew jvmTest

# Run JS tests
./gradlew jsTest
```

## 📝 Migrating from v2.x (Java)

### Major Changes

| v2.x (Java) | v3.0 (Kotlin Multiplatform) |
|-------------|----------------------------|
| `TmdbApi api = new TmdbApi(key);` | `val api = TmdbApi(key)` |
| `MovieDb movie = api.getMovies().getDetails(550, null);` | `val movie = api.movies.getDetails(550)` |
| Blocking calls | Suspending functions (use with coroutines) |
| Jackson annotations | kotlinx.serialization |
| Java HttpClient | Ktor Client |
| Lombok | Kotlin data classes |

### Example Migration

**Before (v2.x Java):**
```java
TmdbApi tmdbApi = new TmdbApi("api-key");
TmdbMovies movies = tmdbApi.getMovies();
MovieDb movie = movies.getDetails(550, "en-US");
System.out.println(movie.getTitle());
```

**After (v3.0 Kotlin):**
```kotlin
val tmdbApi = TmdbApi("api-key")
val movie = tmdbApi.movies.getDetails(550, "en-US")
println(movie.title)
tmdbApi.close()
```

Or with coroutines:
```kotlin
suspend fun getMovie(id: Int) {
    val tmdbApi = TmdbApi("api-key")
    try {
        val movie = tmdbApi.movies.getDetails(id)
        println(movie.title)
    } finally {
        tmdbApi.close()
    }
}
```

## 🎨 Features

### Current Implementation Status

#### ✅ Completed (45+ endpoints)

**Movies API** (18 endpoints)
- Movie details with append-to-response
- Account states
- Alternative titles
- Credits (cast & crew)
- External IDs, Images, Keywords
- Lists, Recommendations, Similar
- Release dates, Reviews
- Translations, Videos
- Watch providers
- Now playing, Popular, Top rated, Upcoming

**Search API** (7 endpoints)
- Search movies, TV shows, people
- Multi-search (all media types)
- Search collections, companies, keywords

**Trending API** (4 endpoints)
- Trending movies, TV shows, people
- All trending content
- Day/week time windows

**Configuration API** (6 endpoints)
- API configuration (image URLs/sizes)
- Countries, Languages
- Departments/Jobs
- Primary translations, Timezones

**Genres API** (2 endpoints)
- Movie genres
- TV show genres

**Collections API** (1 endpoint)
- Collection details with all movies

**People API** (7 endpoints)
- Person details with append-to-response
- Combined credits (movies + TV)
- Movie credits (cast & crew)
- TV credits (cast & crew)
- External IDs (IMDB, social media)
- Profile images
- Translations

#### 🚧 Planned
- TV Series API (detailed endpoints)
- People API (person details, credits)
- Discover API (advanced filtering)
- Authentication API
- Account API
- Reviews, Keywords, Companies APIs
- And more...

## 🤝 Contributing

Contributions are welcome! This is a major rewrite to support Kotlin Multiplatform.

## 📄 License

BSD 2-Clause License - see [LICENCE.txt](LICENCE.txt)

## 🙏 Acknowledgements

- Original Java library by Holger Brandl
- TMDb for providing the excellent movie database API
- JetBrains for Kotlin Multiplatform

## 📚 Resources

- [TMDb API Documentation](https://developer.themoviedb.org/docs/getting-started)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Ktor Client](https://ktor.io/docs/client.html)
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
