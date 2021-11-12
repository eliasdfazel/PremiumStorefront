/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/12/21, 4:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import android.content.Context
import android.util.Log
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.IO.IO
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import org.json.JSONArray
import java.io.File
import java.nio.charset.Charset

class AllContent (val context: Context,
                  val storefrontLiveData: StorefrontLiveData,
                  val queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

    private val generalEndpoint = GeneralEndpoints()

    private val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoint)

    private val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoint)

    private var numberOfPageToRetrieve: Int = 1

    var allLoadingFinished: Boolean = false

    fun retrieveAllApplicationsContent() {

        //Get All Categories
        //Get Applications Inside Each Categories Directory

    }

    fun retrieveAllGamesContent() {

        //Get All Categories
        //Get Games Inside Each Categories Directory

    }

    fun retrieveAllContentWordpress() {

        val allContentFile: File = when (queryType) {
            GeneralEndpoints.QueryType.ApplicationsQuery -> {

                context.getFileStreamPath(IO.UpdateApplicationsDataKey)

            }
            GeneralEndpoints.QueryType.GamesQuery -> {

                context.getFileStreamPath(IO.UpdateGamesDataKey)

            }
            else -> context.getFileStreamPath(IO.UpdateApplicationsDataKey)
        }

        if (allContentFile.exists()) {
            Log.d(this@AllContent.javaClass.simpleName, "Offline Data Available")

            val offlineData = JSONArray(allContentFile.readText(Charset.defaultCharset()))

            storefrontLiveData.processAllContentOffline(offlineData).invokeOnCompletion {

                allLoadingFinished = true

            }

        } else {

            val endpoint = when (queryType) {
                GeneralEndpoints.QueryType.ApplicationsQuery -> {

                    applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint()

                }
                GeneralEndpoints.QueryType.GamesQuery -> {

                    gamesQueryEndpoints.getAllAndroidGamesEndpoint()

                }
                else -> applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint()
            }

            GenericJsonRequest(context, object : JsonRequestResponses {

                override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                    super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                    storefrontLiveData.processAllContent(rawDataJsonArray)

                    if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
                        Log.d(this@AllContent.javaClass.simpleName, "There Might Be More Data To Retrieve")

                        numberOfPageToRetrieve++

                        retrieveAllContentMore()

                    } else {

                        allLoadingFinished = true

                    }

                }

            }).getMethod(endpoint)

        }

    }

    private fun retrieveAllContentMore() {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                storefrontLiveData.processAllContentMore(rawDataJsonArray)

                if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
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

        }).getMethod( when (queryType) {
            GeneralEndpoints.QueryType.ApplicationsQuery -> {

                applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint(numberOfPage = numberOfPageToRetrieve)

            }
            GeneralEndpoints.QueryType.GamesQuery -> {

                gamesQueryEndpoints.getAllAndroidGamesEndpoint(numberOfPage = numberOfPageToRetrieve)

            }
            else -> applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint(numberOfPage = numberOfPageToRetrieve)
        })

    }

}