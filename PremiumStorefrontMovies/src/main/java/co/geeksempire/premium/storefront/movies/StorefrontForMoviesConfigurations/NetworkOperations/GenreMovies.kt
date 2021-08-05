/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/5/21, 9:16 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.GenreDataKey
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.GenreIds
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.StorefrontGenresData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoint

fun retrieveGenreMovies(context: AppCompatActivity, moviesStorefrontLiveData: MoviesStorefrontLiveData) {

    val generalEndpoint = GeneralEndpoints()

    val moviesQueryEndpoint = MoviesQueryEndpoint(generalEndpoint)

    val moviesDocumentSnapshots = ArrayList<StorefrontGenresData>()

    (context.application as PremiumStorefrontApplication)
        .firestoreDatabase
        .document(moviesQueryEndpoint.storefrontMoviesGenreEndpoint())
        .get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                documentSnapshot.toObject(GenreIds::class.java)!!.GenreIds?.forEach { documentMap ->

                    moviesDocumentSnapshots.add(StorefrontGenresData(
                        genreId = documentMap[GenreDataKey.GenreId].toString().toInt(),
                        genreName = documentMap[GenreDataKey.GenreName].toString(),
                        genreIconLink = documentMap[GenreDataKey.GenreIconLink].toString()
                    ))

                }

                moviesStorefrontLiveData.genresMoviesItemData.postValue(moviesDocumentSnapshots)

            }

        }.addOnFailureListener {
            it.printStackTrace()

        }

}