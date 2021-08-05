/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/5/21, 9:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.GenreContent

import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.StorefrontGenresData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GenreData {

    private val allGenreData: HashMap<String, StorefrontGenresData>  = HashMap<String, StorefrontGenresData>()

    fun prepareAllGenresData(inputData: ArrayList<StorefrontGenresData>) = CoroutineScope(Dispatchers.IO).async {

        inputData.forEach {

            allGenreData[it.genreName] = it

        }

    }

    fun getGenreIconByName(categoryName: String) : String? {

        return if (allGenreData.isNotEmpty()) {
            allGenreData[categoryName]?.genreIconLink
        } else {
            null
        }
    }

    fun clearData() {

        allGenreData.clear()

    }

}