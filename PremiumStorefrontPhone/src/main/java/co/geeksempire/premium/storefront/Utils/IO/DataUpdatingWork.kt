/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 5:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.IO

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import co.geeksempire.premium.storefront.NetworkConnections.ApplicationsQueryEndpoint
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import co.geeksempire.premium.storefront.Utils.Notifications.NotificationBuilder
import kotlinx.coroutines.delay
import org.json.JSONArray

class DataUpdatingWork(val appContext: Context, val workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    object Foreground {
        const val NotificationId = 123
    }

    private val notificationBuilder: NotificationBuilder by lazy {
        NotificationBuilder(applicationContext)
    }

    private val generalEndpoint = GeneralEndpoint()

    private val applicationsQueryEndpoint: ApplicationsQueryEndpoint = ApplicationsQueryEndpoint(generalEndpoint)

    override suspend fun doWork(): Result {

        val updateDataKey = workerParams.inputData.getByteArray(IO.UpdateDataKey)?.let { String(it) }

        setForegroundAsync(ForegroundInfo(
            Foreground.NotificationId,
            notificationBuilder.create(
                notificationTitle = applicationContext.getString(R.string.applicationName),
                notificationContent = applicationContext.getString(R.string.updatingApplicationsDataText),
                notificationDone = false)
        ))

        /* Start - Applications Data Updating */
        when (updateDataKey) {
            IO.UpdateApplicationsDataKey -> {

                startContentRetrieval(applicationsQueryEndpoint.getAllAndroidApplicationsEndpoint())

            }
            IO.UpdateGamesDataKey -> {



            }
            IO.UpdateBooksDataKey -> {



            }
            IO.UpdateMoviesDataKey -> {



            }
        }

        /* End - Applications Data Updating */

        delay(1111)

        setForegroundAsync(ForegroundInfo(Foreground.NotificationId, notificationBuilder.create(notificationDone = true)))

        return Result.success()
    }

    private fun startContentRetrieval(endpointAddress: String) {

        GenericJsonRequest(applicationContext, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)



                if (rawDataJsonArray.length() == applicationsQueryEndpoint.defaultProductsPerPage) {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    startContentRetrieval(endpointAddress)

                } else {



                }

            }

        }).getMethod(endpointAddress)

    }

}