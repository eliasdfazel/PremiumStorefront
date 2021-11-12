/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/12/21, 6:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import org.json.JSONArray

fun retrieveOldContent(context: AppCompatActivity,
                       storefrontLiveData: StorefrontLiveData,
                       generalEndpoints: GeneralEndpoints,
                       queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoints)

    val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoints)

    val queryEndpoint = when (queryType) {
        GeneralEndpoints.QueryType.ApplicationsQuery -> {

            applicationsQueryEndpoints.getOldApplicationsEndpoint(numberOfProducts = (columnCount(context, 113) * 5))
        }
        GeneralEndpoints.QueryType.GamesQuery -> {

            gamesQueryEndpoints.getOldGamesEndpoint(numberOfProducts = (columnCount(context, 113) * 5))
        }
        else -> applicationsQueryEndpoints.getOldApplicationsEndpoint(numberOfProducts = (columnCount(context, 113) * 5))
    }

    GenericJsonRequest(context, object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            storefrontLiveData.processOldContentWordpress(rawDataJsonArray)

        }

    }).getMethod(queryEndpoint)

}