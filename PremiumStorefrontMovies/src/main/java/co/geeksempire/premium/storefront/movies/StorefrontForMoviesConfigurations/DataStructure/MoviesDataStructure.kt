/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/5/21, 11:26 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure

import androidx.annotation.Keep

object FeaturedMoviesDataKey {
    const val ProductId = "ProductId"
    const val MovieGenre = "MovieGenre"
}

object MoviesDataKey {
    const val MovieId= "Movie Id"
    const val MovieGenre =  "Movie Genre"

    const val MoviePurchasePrice = "Movie Purchasing Price"
    const val MovieRentingPrice = "Movie Renting Price"

    const val MovieTrailer = "Movie Trailer"

    const val MovieSubtitleLanguages = "Movie Subtitle Languages"
    const val MovieAudioLanguages = "Movie Audio Languages"

    const val MovieStars = "Movie Stars"
    const val MovieStudio = "Movie Studio"
    const val MovieDirectors = "Movie Directors"

    const val MovieReleaseData = "Movie Release Date"

    const val MovieContentSafetyRating = "Content Safety Rating"

    const val MovieRating = "Rating"

    const val MovieProductId = "productId"

    const val MovieName = "productName"
    const val MovieDescription = "productDescription"
    const val MovieSummary = "productSummary"
    const val MoviePoster = "productPoster"

    const val MovieUniqueRecommendation = "uniqueRecommendation"
}

class MoviesDataStructure(private val movieData: MutableMap<String, Any>) {

    fun movieId() : String = movieData[MoviesDataKey.MovieId].toString()
    fun movieGenre() : String = movieData[MoviesDataKey.MovieGenre].toString()

    fun moviePurchasePrice() : String = movieData[MoviesDataKey.MoviePurchasePrice].toString()
    fun movieRentingPrice() : String = movieData[MoviesDataKey.MovieRentingPrice].toString()

    fun movieTrailer() : String = movieData[MoviesDataKey.MovieTrailer].toString()

    fun movieSubtitleLanguages() : String = movieData[MoviesDataKey.MovieSubtitleLanguages].toString()
    fun movieAudioLanguages() : String = movieData[MoviesDataKey.MovieAudioLanguages].toString()

    fun movieStars() : String = movieData[MoviesDataKey.MovieStars].toString()
    fun movieStudio() : String = movieData[MoviesDataKey.MovieStudio].toString()
    fun movieDirectors() : String = movieData[MoviesDataKey.MovieDirectors].toString()

    fun movieReleaseDate() : String = movieData[MoviesDataKey.MovieReleaseData].toString()
    fun movieContentSafetyRating() : String = movieData[MoviesDataKey.MovieContentSafetyRating].toString()

    fun movieRating() : String = movieData[MoviesDataKey.MovieRating].toString()

    fun movieProductId() : String = movieData[MoviesDataKey.MovieProductId].toString()

    fun movieName() : String = movieData[MoviesDataKey.MovieName].toString()
    fun movieDescription() : String = movieData[MoviesDataKey.MovieDescription].toString()
    fun movieSummary() : String = movieData[MoviesDataKey.MovieSummary].toString()
    fun moviePoster() : String = movieData[MoviesDataKey.MoviePoster].toString()

    fun movieUniqueRecommendation() : String = movieData[MoviesDataKey.MovieUniqueRecommendation].toString()

}

object GenreDataKey {
    const val GenreId = "genreId"
    const val GenreName = "genreName"
    const val GenreIconLink = "genreIconLink"
    const val ProductCount = "productCount"
}

@Keep
data class GenreIds(var GenreIds: ArrayList<HashMap<String, Any>>? = null)

@Keep
data class StorefrontGenresData(var genreId: Int, var genreName: String, var genreIconLink: String, var productCount: Int, var selectedCategory: Boolean = false)