/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/29/21, 10:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

fun retrieveCategories(context: AppCompatActivity,
                       generalEndpoints: GeneralEndpoints,
                       storefrontLiveData: StorefrontLiveData,
                       firebaseRemoteConfiguration: FirebaseRemoteConfig,
                       queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoints)

    val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoints)

    val queryEndpoint = when (queryType) {
        GeneralEndpoints.QueryType.ApplicationsQuery -> {

            applicationsQueryEndpoints.storefrontApplicationsCategoryEndpoint()

        }
        GeneralEndpoints.QueryType.GamesQuery -> {

            gamesQueryEndpoints.storefrontGamesCategoryEndpoint()

        }
        else -> applicationsQueryEndpoints.storefrontApplicationsCategoryEndpoint()
    }

    (context.application as PremiumStorefrontApplication)
        .firestoreDatabase
        .document(queryEndpoint)
        .get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                storefrontLiveData.processCategoryData(documentSnapshot)

            }

        }.addOnFailureListener {
            it.printStackTrace()

        }

}