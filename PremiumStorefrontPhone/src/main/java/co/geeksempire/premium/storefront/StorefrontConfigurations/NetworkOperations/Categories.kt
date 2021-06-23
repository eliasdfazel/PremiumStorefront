/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/23/21, 9:02 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.NetworkConnections.ApplicationsQueryEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.Notifications.RemoteConfigurationKey
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray

fun Storefront.retrieveCategories() {

    val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    firebaseRemoteConfiguration.setConfigSettingsAsync(remoteConfigSettings {
        minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
            1
        } else {
            (60 * 60/* One Hour */) * 7
        }
    })
    firebaseRemoteConfiguration.fetchAndActivate()
        .addOnSuccessListener {

            GenericJsonRequest(applicationContext, object : JsonRequestResponses {

                override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                    super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                    storefrontLiveData.processCategoriesList(rawDataJsonArray)

                }

            }).getMethod(applicationsQueryEndpoint.getApplicationsCategoriesEndpoint(csvExclusions = firebaseRemoteConfiguration.getString(RemoteConfigurationKey.Applications_Categories_Exclusion)))

        }

}