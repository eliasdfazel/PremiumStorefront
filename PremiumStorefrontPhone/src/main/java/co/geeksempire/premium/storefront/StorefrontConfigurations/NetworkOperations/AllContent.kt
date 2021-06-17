/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/17/21, 10:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import android.content.Context
import android.util.Log
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.ProductSearchEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import org.json.JSONArray

class AllContent (val context: Context, val storefrontLiveData: StorefrontLiveData) {

    private val generalEndpoint = GeneralEndpoint()

    private val productSearchEndpoint: ProductSearchEndpoint = ProductSearchEndpoint(generalEndpoint)

    private var numberOfPageToRetrieve: Int = 1

    var allLoadingFinished: Boolean = false

    fun retrieveAllContent() {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                storefrontLiveData.processAllContent(rawDataJsonArray)

                if (rawDataJsonArray.length() == productSearchEndpoint.defaultProductsPerPage) {
                    Log.d(this@AllContent.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    numberOfPageToRetrieve++

                    retrieveAllContentMore()

                } else {

                    allLoadingFinished = true

                }

            }

        }).getMethod(productSearchEndpoint.getAllProductsShowcaseEndpoint())

    }

    fun retrieveAllContentMore() {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                storefrontLiveData.processAllContentMore(rawDataJsonArray)

                if (rawDataJsonArray.length() == productSearchEndpoint.defaultProductsPerPage) {
                    Log.d(this@AllContent.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    numberOfPageToRetrieve++

                    retrieveAllContentMore()

                } else {

                    allLoadingFinished = true

                }

            }

            override fun jsonRequestResponseFailureHandler(jsonError: String?) {
                super.jsonRequestResponseFailureHandler(jsonError)

            }

            override fun jsonRequestResponseFailureHandler(networkError: Int?) {
                super.jsonRequestResponseFailureHandler(networkError)

            }

        }).getMethod(productSearchEndpoint.getAllProductsShowcaseEndpoint(numberOfPage = numberOfPageToRetrieve))

    }

}