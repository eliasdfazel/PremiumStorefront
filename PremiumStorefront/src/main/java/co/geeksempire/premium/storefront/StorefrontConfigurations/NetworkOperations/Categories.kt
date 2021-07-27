/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 6:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections.ApplicationsQueryEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections.GamesQueryEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.Notifications.RemoteConfigurationKey
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray

fun retrieveCategories(context: AppCompatActivity,
                       generalEndpoint: GeneralEndpoint,
                       storefrontLiveData: StorefrontLiveData,
                       firebaseRemoteConfiguration: FirebaseRemoteConfig,
                       queryType: String = GeneralEndpoint.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    val gamesQueryEndpoint: GamesQueryEndpoint = GamesQueryEndpoint(generalEndpoint)

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
                GeneralEndpoint.QueryType.ApplicationsQuery -> {

                    applicationsQueryEndpoint.getApplicationsCategoriesEndpoint(csvExclusions = firebaseRemoteConfiguration.getString(RemoteConfigurationKey.Applications_Categories_Exclusion))
                }
                GeneralEndpoint.QueryType.GamesQuery -> {

                    gamesQueryEndpoint.getGamesCategoriesEndpoint(csvExclusions = firebaseRemoteConfiguration.getString(RemoteConfigurationKey.Games_Categories_Exclusion))

                }
                else -> applicationsQueryEndpoint.getApplicationsCategoriesEndpoint(csvExclusions = firebaseRemoteConfiguration.getString(RemoteConfigurationKey.Applications_Categories_Exclusion))
            }

            GenericJsonRequest(context, object : JsonRequestResponses {

                override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                    super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                    storefrontLiveData.processCategoriesList(rawDataJsonArray)

                }

            }).getMethod(queryEndpoint)

        }

}