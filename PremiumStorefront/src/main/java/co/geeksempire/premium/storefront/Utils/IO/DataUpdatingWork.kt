/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/15/21, 9:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.IO

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.geeksempire.premium.storefront.Database.Write.InputProcess
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import kotlinx.coroutines.delay
import org.json.JSONArray

class DataUpdatingWork(val appContext: Context, val workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    private val generalEndpoint = GeneralEndpoints()

    private val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoint)
    private val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoint)

    private val inputProcess: InputProcess by lazy {
        InputProcess(applicationContext)
    }

    var stringBuilder = StringBuilder()

    private var numberOfPageToRetrieve: Int = 1

    override suspend fun doWork(): Result {

        val updateDataKey = workerParams.inputData.getByteArray(IO.UpdateDataKey)?.let { String(it) }
        Log.d(this@DataUpdatingWork.javaClass.simpleName, updateDataKey.toString())

        /* Start - Applications Data Updating */
        when (updateDataKey) {
            IO.UpdateApplicationsDataKey -> {

                startApplicationsContentRetrieval(IO.UpdateApplicationsDataKey)

            }
            IO.UpdateGamesDataKey -> {

                startGamesContentRetrieval(IO.UpdateGamesDataKey)

            }
            IO.UpdateBooksDataKey -> {



            }
            IO.UpdateMoviesDataKey -> {

                //Get List Genres
                    //Then Get Genres Collections

            }
        }

        /* End - Applications Data Updating */

        delay(1357)

        return Result.success()
    }

    private fun startApplicationsContentRetrieval(updateDataKey: String) {

        GenericJsonRequest(applicationContext, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    stringBuilder.append(rawDataJsonArray.toString())

                    numberOfPageToRetrieve++

                    startApplicationsContentRetrieval(updateDataKey)

                } else {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "No More Content")

                    stringBuilder.append(rawDataJsonArray.toString())

                    inputProcess.writeDataToFile(updateDataKey, stringBuilder.toString())

                }

            }

        }).getMethod(applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint(productPerPage = 99, numberOfPage = numberOfPageToRetrieve))

    }

    private fun startGamesContentRetrieval(updateDataKey: String) {

        GenericJsonRequest(applicationContext, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    stringBuilder.append(rawDataJsonArray.toString())

                    numberOfPageToRetrieve++

                    startApplicationsContentRetrieval(updateDataKey)

                } else {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "No More Content")

                    stringBuilder.append(rawDataJsonArray.toString())

                    inputProcess.writeDataToFile(updateDataKey, stringBuilder.toString())

                }

            }

        }).getMethod(gamesQueryEndpoints.getAllAndroidGamesEndpoint(productPerPage = 99, numberOfPage = numberOfPageToRetrieve))

    }

}