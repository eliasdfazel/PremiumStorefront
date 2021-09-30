/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/30/21, 9:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.FeaturedContentsDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.FirestoreProductDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsIds
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import org.json.JSONArray

fun retrieveFeaturedContent(context: AppCompatActivity,
                            storefrontLiveData: StorefrontLiveData,
                            generalEndpoints: GeneralEndpoints,
                            queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoints)

    val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoints)

    val queryEndpoint = when (queryType) {
        GeneralEndpoints.QueryType.ApplicationsQuery -> {

            applicationsQueryEndpoints.storefrontFeaturedApplicationsEndpoint()
        }
        GeneralEndpoints.QueryType.GamesQuery -> {

            gamesQueryEndpoints.storefrontFeaturedGamesEndpoint()
        }
        else -> applicationsQueryEndpoints.storefrontFeaturedApplicationsEndpoint()
    }

    val contentsDocumentSnapshots = ArrayList<DocumentSnapshot>()

    (context.application as PremiumStorefrontApplication)
        .firestoreDatabase
        .document(queryEndpoint)
        .get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                val productsIds = documentSnapshot.toObject(ProductsIds::class.java)!!.ProductsIds

                var featuredMoviesCounter = 0

                productsIds?.forEach {

                    val productId = it[FeaturedContentsDataKey.ProductId].toString()
                    val categoryName = it[FeaturedContentsDataKey.ProductCategory].toString()

                    val querySpecificEndpoint = when (queryType) {
                        GeneralEndpoints.QueryType.ApplicationsQuery -> {

                            applicationsQueryEndpoints.firestoreSpecificApplication(categoryName, productId)

                        }
                        GeneralEndpoints.QueryType.GamesQuery -> {

                            gamesQueryEndpoints.firestoreSpecificGame(categoryName, productId)

                        }
                        else -> applicationsQueryEndpoints.firestoreSpecificApplication(categoryName, productId)
                    }

                    (context.application as PremiumStorefrontApplication)
                        .firestoreDatabase
                        .document(querySpecificEndpoint)
                        .get(Source.SERVER).addOnSuccessListener { movieDocumentSnapshot ->

                            featuredMoviesCounter++

                            if (movieDocumentSnapshot.exists()) {
                                Log.d("Featured ${queryType}", movieDocumentSnapshot.data?.get(FirestoreProductDataKey.ProductName).toString())

                                contentsDocumentSnapshots.add(movieDocumentSnapshot)

                                if (productsIds.size == featuredMoviesCounter) {

                                    storefrontLiveData.featuredContentItems.postValue(contentsDocumentSnapshots)

                                }

                            }

                        }

                }

            }

        }.addOnFailureListener {
            it.printStackTrace()

        }

}

fun retrieveFeaturedContentWordpress(context: AppCompatActivity,
                            storefrontLiveData: StorefrontLiveData,
                            generalEndpoints: GeneralEndpoints,
                            queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoints)

    val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoints)

    val queryEndpoint = when (queryType) {
        GeneralEndpoints.QueryType.ApplicationsQuery -> {

            applicationsQueryEndpoints.getFeaturedApplicationsEndpoint()
        }
        GeneralEndpoints.QueryType.GamesQuery -> {

            gamesQueryEndpoints.getFeaturedGamesEndpoint()
        }
        else -> applicationsQueryEndpoints.getFeaturedApplicationsEndpoint()
    }

    GenericJsonRequest(context, object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            storefrontLiveData.processFeaturedContent(rawDataJsonArray)

        }

    }).getMethod(queryEndpoint)

}