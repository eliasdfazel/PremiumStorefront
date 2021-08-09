/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/9/21, 10:40 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter

import android.util.Log
import androidx.annotation.Keep
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

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

class FilterAllMovies (private val moviesStorefrontLiveData: MoviesStorefrontLiveData) {

    fun filterAllMoviesByGenre(storefrontAllMovies: ArrayList<DocumentSnapshot>,
                                    selectedCategory: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllMovies.javaClass.simpleName, "Selected Category: ${selectedCategory}")

        storefrontAllMovies.forEach {

            if (it.exists()) {

                it.data?.let { moviesHashMap ->

                    val moviesDataStructure = MoviesDataStructure(moviesHashMap)

                    val storefrontAllContentsFilter = storefrontAllMovies.filter {

                        (moviesDataStructure.movieGenres() == selectedCategory)
                    }

                    storefrontAllMovies.clear()
                    storefrontAllMovies.addAll(storefrontAllContentsFilter)

                    moviesStorefrontLiveData.allFilteredMoviesItemData.postValue(Pair(storefrontAllMovies, false))

                }

            }

        }

    }

}