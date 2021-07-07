/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/7/21, 9:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.NetworkConnections.ApplicationsQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GamesQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import org.json.JSONArray

fun retrieveOldContent(context: AppCompatActivity,
                       storefrontLiveData: StorefrontLiveData,
                       generalEndpoint: GeneralEndpoint,
                       queryType: String = GeneralEndpoint.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    val gamesQueryEndpoint: GamesQueryEndpoint = GamesQueryEndpoint(generalEndpoint)

    val queryEndpoint = when (queryType) {
        GeneralEndpoint.QueryType.ApplicationsQuery -> {

            applicationsQueryEndpoint.getOldApplicationsEndpoint(numberOfProducts = (columnCount(context, 113) * 5))
        }
        GeneralEndpoint.QueryType.GamesQuery -> {

            gamesQueryEndpoint.getOldGamesEndpoint(numberOfProducts = (columnCount(context, 113) * 5))
        }
        else -> applicationsQueryEndpoint.getOldApplicationsEndpoint(numberOfProducts = (columnCount(context, 113) * 5))
    }

    GenericJsonRequest(context, object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            storefrontLiveData.processOldContent(rawDataJsonArray)

        }

    }).getMethod(queryEndpoint)

}