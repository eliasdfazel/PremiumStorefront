/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/9/21, 8:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter

import androidx.annotation.Keep

object SortingOptions {
    const val SortByPrice = "productPrice"
    const val SortByRating = "Rating"
}

object FilteringOptions {
    const val FilterByCountry = "FilterByCountry"
    const val FilterByStudio = "FilterByStudio"
}

@Keep
data class FilterOptionsItem(var filterOptionLabel: String, var filterOptionIconLink: String?)

class FilterAllMovies {


}