/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/27/21, 11:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.NetworkConnections.ApplicationsQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GamesQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import org.json.JSONArray

fun retrieveNewContent(context: AppCompatActivity,
                       storefrontLiveData: StorefrontLiveData,
                       generalEndpoint: GeneralEndpoint,
                       queryType: String = GeneralEndpoint.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    val gamesQueryEndpoint: GamesQueryEndpoint = GamesQueryEndpoint(generalEndpoint)

    val queryEndpoint = when (queryType) {
        GeneralEndpoint.QueryType.ApplicationsQuery -> {

            applicationsQueryEndpoint.getNewApplicationsEndpoint((columnCount(context, 271) * 3) + 3)
        }
        GeneralEndpoint.QueryType.GamesQuery -> {

            gamesQueryEndpoint.getNewGamesEndpoint((columnCount(context, 271) * 3) + 3)
        }
        else -> applicationsQueryEndpoint.getNewApplicationsEndpoint((columnCount(context, 271) * 3) + 3)
    }

    GenericJsonRequest(context, object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            storefrontLiveData.processNewContent(rawDataJsonArray)

        }

    }).getMethod(queryEndpoint)

}