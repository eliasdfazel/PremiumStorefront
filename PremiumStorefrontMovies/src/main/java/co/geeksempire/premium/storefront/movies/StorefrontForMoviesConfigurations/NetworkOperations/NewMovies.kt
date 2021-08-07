/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/7/21, 5:06 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkOperations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.NetworkEndpoints.MoviesQueryEndpoints
import org.json.JSONArray

fun retrieveNewContent(context: AppCompatActivity,
                       moviesStorefrontLiveData: MoviesStorefrontLiveData,
                       generalEndpoints: GeneralEndpoints) {

    val moviesQueryEndpoints: MoviesQueryEndpoints = MoviesQueryEndpoints(generalEndpoints)

    val queryEndpoint = moviesQueryEndpoints.getNewMoviesEndpoint(numberOfProducts = (columnCount(context, 279) * 5) + 3)

    GenericJsonRequest(context, object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            moviesStorefrontLiveData.processNewContent(rawDataJsonArray)

        }

    }).getMethod(queryEndpoint)

}