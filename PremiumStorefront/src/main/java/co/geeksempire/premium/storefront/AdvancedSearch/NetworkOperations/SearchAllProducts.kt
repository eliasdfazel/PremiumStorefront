/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 4:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.NetworkOperations

import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import org.json.JSONArray

class SearchAllProducts (private val context: CompleteSearch) {

    val generalEndpoints = GeneralEndpoints()

    fun startApplicationsSearch(searchQuery: String) {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                context.completeSearchLiveData.processAllContentWordpress(rawDataJsonArray)

            }

        }).getMethod(generalEndpoints.getCompleteSearchEndpoint(searchQuery, GeneralEndpoints.QueryType.ApplicationsQuery))

    }

    fun startGamesSearch(searchQuery: String) {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                context.completeSearchLiveData.processAllContentWordpress(rawDataJsonArray)

            }

        }).getMethod(generalEndpoints.getCompleteSearchEndpoint(searchQuery, GeneralEndpoints.QueryType.GamesQuery))

    }

    fun startMoviesSearch(searchQuery: String) {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                context.completeSearchLiveData.processAllContentWordpress(rawDataJsonArray)

            }

        }).getMethod(generalEndpoints.getCompleteSearchEndpoint(searchQuery, GeneralEndpoints.QueryType.MoviesQuery))

    }

}