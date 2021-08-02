/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 9:04 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints

import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints

class MoviesQueryEndpoint (private val generalEndpoints: GeneralEndpoints) {

    val generalStorefrontMoviesEndpoint = generalEndpoints.generalStorefrontDatabaseEndpoint +
            "/" +
            "Multimedia" +
            "/" +
            "Movies"

    fun storefrontSpecificMovieEndpoint(movieGenre: String, movieId: String) = generalStorefrontMoviesEndpoint +
            "/" +
            movieGenre +
            "/" +
            movieId

    fun storefrontFeaturedMoviesEndpoint() = generalStorefrontMoviesEndpoint +
            "/" +
            "Featured" +
            "/" +
            "Content"

}