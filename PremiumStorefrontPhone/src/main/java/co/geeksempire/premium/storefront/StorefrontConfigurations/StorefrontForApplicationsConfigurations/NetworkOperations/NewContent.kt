/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/27/21, 11:36 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.NetworkConnections.ApplicationsQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import org.json.JSONArray

fun retrieveNewContent(context: AppCompatActivity,
                       storefrontLiveData: StorefrontLiveData,
                       generalEndpoint: GeneralEndpoint) {

    val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    GenericJsonRequest(context, object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            storefrontLiveData.processNewContent(rawDataJsonArray)

        }

    }).getMethod(applicationsQueryEndpoint.getNewApplicationsEndpoint((
            columnCount(context, 271) * 3) + 3
    ))

}