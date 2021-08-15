/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/15/21, 10:33 AM
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
import com.google.firebase.firestore.QuerySnapshot
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

                        val rawQuerySnapshot = ArrayList<QuerySnapshot>()

                        val allGenreCount = availableGenre.size

                        var genreCounter = 0

                        availableGenre.forEachIndexed { index, storefrontGenresData ->

                            (context.application as PremiumStorefrontApplication)
                                .firestoreDatabase
                                .collection(moviesQueryEndpoint.storefrontMoviesGenreCollectionsEndpoint(storefrontGenresData.genreName))
                                .get().addOnSuccessListener { querySnapshot ->

                                    genreCounter++

                                    if (!querySnapshot.isEmpty) {

                                        rawQuerySnapshot.add(querySnapshot)

                                    }

                                    if (allGenreCount == genreCounter) {

                                        moviesStorefrontLiveData.processAllMoviesData(rawQuerySnapshot)

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