/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/29/21, 10:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.Notifications.RemoteConfigurationKey
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray

fun retrieveCategories(context: AppCompatActivity,
                       generalEndpoints: GeneralEndpoints,
                       storefrontLiveData: StorefrontLiveData,
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

fun retrieveCategoriesWordpress(context: AppCompatActivity,
                       generalEndpoints: GeneralEndpoints,
                       storefrontLiveData: StorefrontLiveData,
                       firebaseRemoteConfiguration: FirebaseRemoteConfig,
                       queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoints)

    val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoints)

    firebaseRemoteConfiguration.setConfigSettingsAsync(remoteConfigSettings {
        minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
            1
        } else {
            (60 * 60/* One Hour */) * 7
        }
    })
    firebaseRemoteConfiguration.fetchAndActivate()
        .addOnSuccessListener {

            val queryEndpoint = when (queryType) {
                GeneralEndpoints.QueryType.ApplicationsQuery -> {

                    applicationsQueryEndpoints.getApplicationsCategoriesEndpoint(csvExclusions = firebaseRemoteConfiguration.getString(
                        RemoteConfigurationKey.Applications_Categories_Exclusion))
                }
                GeneralEndpoints.QueryType.GamesQuery -> {

                    gamesQueryEndpoints.getGamesCategoriesEndpoint(csvExclusions = firebaseRemoteConfiguration.getString(
                        RemoteConfigurationKey.Games_Categories_Exclusion))

                }
                else -> applicationsQueryEndpoints.getApplicationsCategoriesEndpoint(csvExclusions = firebaseRemoteConfiguration.getString(
                    RemoteConfigurationKey.Applications_Categories_Exclusion))
            }

            GenericJsonRequest(context, object : JsonRequestResponses {

                override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                    super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                    storefrontLiveData.processCategoriesList(rawDataJsonArray)

                }

            }).getMethod(queryEndpoint)

        }

}