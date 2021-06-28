/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 2:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.NetworkOperations

import android.content.Context
import android.util.Log
import co.geeksempire.premium.storefront.NetworkConnections.ApplicationsQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GamesQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.Utils.IO.IO
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import org.json.JSONArray
import java.io.File
import java.nio.charset.Charset

class AllContent (val context: Context,
                  val storefrontLiveData: StorefrontLiveData,
                  val queryType: String = GeneralEndpoint.QueryType.ApplicationsQuery) {

    private val generalEndpoint = GeneralEndpoint()

    private val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    private val gamesQueryEndpoint: GamesQueryEndpoint = GamesQueryEndpoint(generalEndpoint)

    private var numberOfPageToRetrieve: Int = 1

    var allLoadingFinished: Boolean = false

    fun retrieveAllContent() {

        val allContentFile: File = when (queryType) {
            GeneralEndpoint.QueryType.ApplicationsQuery -> {

                context.getFileStreamPath(IO.UpdateApplicationsDataKey)

            }
            GeneralEndpoint.QueryType.GamesQuery -> {

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
                GeneralEndpoint.QueryType.ApplicationsQuery -> {

                    applicationsQueryEndpoint.getAllAndroidApplicationsEndpoint()

                }
                GeneralEndpoint.QueryType.GamesQuery -> {

                    gamesQueryEndpoint.getAllAndroidGamesEndpoint()

                }
                else -> applicationsQueryEndpoint.getAllAndroidApplicationsEndpoint()
            }

            GenericJsonRequest(context, object : JsonRequestResponses {

                override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                    super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                    storefrontLiveData.processAllContent(rawDataJsonArray)

                    if (rawDataJsonArray.length() == applicationsQueryEndpoint.defaultProductsPerPage) {
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

    fun retrieveAllContentMore() {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                storefrontLiveData.processAllContentMore(rawDataJsonArray)

                if (rawDataJsonArray.length() == applicationsQueryEndpoint.defaultProductsPerPage) {
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
            GeneralEndpoint.QueryType.ApplicationsQuery -> {

                applicationsQueryEndpoint.getAllAndroidApplicationsEndpoint(numberOfPage = numberOfPageToRetrieve)

            }
            GeneralEndpoint.QueryType.GamesQuery -> {

                gamesQueryEndpoint.getAllAndroidGamesEndpoint(numberOfPage = numberOfPageToRetrieve)

            }
            else -> applicationsQueryEndpoint.getAllAndroidApplicationsEndpoint(numberOfPage = numberOfPageToRetrieve)
        })

    }

}