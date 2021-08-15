/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/15/21, 11:35 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsIds
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.FeaturedMoviesDataKey
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataKey
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoints
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source

fun retrieveFeaturedMovies(context: AppCompatActivity, moviesStorefrontLiveData: MoviesStorefrontLiveData) {

    val generalEndpoint = GeneralEndpoints()

    val moviesQueryEndpoint = MoviesQueryEndpoints(generalEndpoint)

    val moviesDocumentSnapshots = ArrayList<DocumentSnapshot>()

    (context.application as PremiumStorefrontApplication)
        .firestoreDatabase
        .document(moviesQueryEndpoint.storefrontFeaturedMoviesEndpoint())
        .get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                val productsIds = documentSnapshot.toObject(ProductsIds::class.java)!!.ProductsIds

                var featuredMoviesCounter = 0

                productsIds?.forEach {

                    val productId = it[FeaturedMoviesDataKey.ProductId].toString()
                    val movieGenre = it[FeaturedMoviesDataKey.MovieGenre].toString()

                    (context.application as PremiumStorefrontApplication)
                        .firestoreDatabase
                        .document(moviesQueryEndpoint.storefrontSpecificMovieEndpoint(movieGenre, productId))
                        .get(Source.SERVER).addOnSuccessListener { movieDocumentSnapshot ->

                            featuredMoviesCounter++

                            if (movieDocumentSnapshot.exists()) {
                                Log.d("Featured Movies", movieDocumentSnapshot.data?.get(MoviesDataKey.MovieName).toString())

                                moviesDocumentSnapshots.add(movieDocumentSnapshot)

                                if (productsIds.size == featuredMoviesCounter) {

                                    moviesStorefrontLiveData.featuredContentItemData.postValue(moviesDocumentSnapshots)

                                }

                            }

                        }

                }

            }

        }.addOnFailureListener {
            it.printStackTrace()

        }

}