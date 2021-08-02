/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 11:00 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsIds
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.FeaturedMoviesDataKey
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoint
import com.google.firebase.firestore.Source

fun retrieveFeaturedMovies(context: AppCompatActivity, moviesStorefrontLiveData: MoviesStorefrontLiveData) {

    val generalEndpoint = GeneralEndpoints()

    val moviesQueryEndpoint = MoviesQueryEndpoint(generalEndpoint)

    (context.application as PremiumStorefrontApplication)
        .firestoreDatabase
        .document(moviesQueryEndpoint.storefrontFeaturedMoviesEndpoint())
        .get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                documentSnapshot.toObject(ProductsIds::class.java)!!.ProductsIds?.forEach {

                    val productId = it[FeaturedMoviesDataKey.ProductId].toString()
                    val movieGenre = it[FeaturedMoviesDataKey.MovieGenre].toString()

                    (context.application as PremiumStorefrontApplication)
                        .firestoreDatabase
                        .document(moviesQueryEndpoint.storefrontSpecificMovieEndpoint(movieGenre, productId))
                        .get(Source.SERVER).addOnSuccessListener { movieDocumentSnapshot ->

                            if (movieDocumentSnapshot.exists()) {

                                moviesStorefrontLiveData.featuredContentItemData.postValue(movieDocumentSnapshot)

                            }

                        }

                }

            }

        }.addOnFailureListener {
            it.printStackTrace()

        }

}