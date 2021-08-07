/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/7/21, 8:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints

import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints

class MoviesQueryEndpoints (private val generalEndpoints: GeneralEndpoints) {

    val defaultProductsPerPage = 19
    val defaultNumberOfPage = 1

    /*
     * Firestore Endpoint
     */

    val generalStorefrontMoviesEndpoint : String  = generalEndpoints.generalStorefrontDatabaseEndpoint +
            "/" +
            "Multimedia" +
            "/" +
            "Movies"

    fun storefrontSpecificMovieEndpoint(movieGenre: String, movieId: String) : String  = generalStorefrontMoviesEndpoint +
            "/" +
            movieGenre +
            "/" +
            movieId

    fun storefrontFeaturedMoviesEndpoint() : String  = generalStorefrontMoviesEndpoint +
            "/" +
            "Featured" +
            "/" +
            "Content"

    fun storefrontMoviesGenreEndpoint() : String = generalEndpoints.generalStorefrontDatabaseEndpoint +
            "/" +
            "Multimedia" +
            "/" +
            "Movies"

    fun storefrontMoviesGenreCollectionsEndpoint(genreName: String) : String = storefrontMoviesGenreEndpoint() +
            "/" +
            genreName

    /*
     * Wordpress Endpoint
     */

    fun getAllMultimediaMoviesEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) =
        "https://geeksempire.co/wp-json/wc/v3/products?" +
                "consumer_key=${generalEndpoints.consumerKey()}" +
                "&" +
                "consumer_secret=${generalEndpoints.consumerSecret()}" +
                "&" +
                "per_page=${productPerPage}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "category=${GeneralEndpoints.QueryType.MoviesQuery}" +
                "&" +
                "orderby=date" +
                "&" +
                "order=desc"

    fun getNewMoviesEndpoint(numberOfProducts: Int = 3, numberOfPage: Int = 1) : String =
        "${getAllMultimediaMoviesEndpoint()}" +
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "orderby=date" +
                "&" +
                "order=desc"

}