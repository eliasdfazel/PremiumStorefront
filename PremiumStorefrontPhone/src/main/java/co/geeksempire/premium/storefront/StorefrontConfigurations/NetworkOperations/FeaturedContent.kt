/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 4:48 AM
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
import org.json.JSONArray

fun retrieveFeaturedContent(context: AppCompatActivity,
                            storefrontLiveData: StorefrontLiveData,
                            generalEndpoint: GeneralEndpoint,
                            queryType: String = GeneralEndpoint.QueryType.ApplicationsQuery) {

    val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    val gamesQueryEndpoint: GamesQueryEndpoint = GamesQueryEndpoint(generalEndpoint)

    val queryEndpoint = when (queryType) {
        GeneralEndpoint.QueryType.ApplicationsQuery -> {

            applicationsQueryEndpoint.getFeaturedApplicationsEndpoint()
        }
        GeneralEndpoint.QueryType.GamesQuery -> {

            gamesQueryEndpoint.getFeaturedGamesEndpoint()
        }
        else -> applicationsQueryEndpoint.getFeaturedApplicationsEndpoint()
    }

    GenericJsonRequest(context, object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            storefrontLiveData.processFeaturedContent(rawDataJsonArray)

        }

    }).getMethod(queryEndpoint)

}