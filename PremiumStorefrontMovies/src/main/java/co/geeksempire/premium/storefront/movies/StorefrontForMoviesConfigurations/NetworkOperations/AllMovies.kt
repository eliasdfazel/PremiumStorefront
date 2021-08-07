/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/7/21, 9:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoints
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun retrieveAllMovies(context: AppCompatActivity, moviesStorefrontLiveData: MoviesStorefrontLiveData) {

    val generalEndpoint = GeneralEndpoints()

    val moviesQueryEndpoint = MoviesQueryEndpoints(generalEndpoint)

    (context.application as PremiumStorefrontApplication)
        .firestoreDatabase
        .document(moviesQueryEndpoint.storefrontMoviesGenreEndpoint())
        .get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                context.lifecycleScope.launch {

                    moviesStorefrontLiveData.processAllMoviesGenreData(documentSnapshot).collect { availableGenre ->

                        availableGenre.forEach { genre ->

                            (context.application as PremiumStorefrontApplication)
                                .firestoreDatabase
                                .collection(moviesQueryEndpoint.storefrontMoviesGenreCollectionsEndpoint(genre.genreName))
                                .get().addOnSuccessListener { querySnapshot ->

                                    if (!querySnapshot.isEmpty) {

                                        context.lifecycleScope.launch {

                                            moviesStorefrontLiveData.processAllMoviesData(querySnapshot)

                                        }

                                    }

                                }

                        }

                    }

                }

            }

        }.addOnFailureListener {
            it.printStackTrace()

        }

}