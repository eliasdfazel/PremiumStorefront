/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 5:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.IO

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Notifications.NotificationBuilder
import kotlinx.coroutines.delay

class DataUpdatingWork(val appContext: Context, val workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    object Foreground {
        const val NotificationId = 123
    }

    val notificationBuilder: NotificationBuilder by lazy {
        NotificationBuilder(applicationContext)
    }

    private var workerResult: Result = Result.failure()

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



        /* End - Applications Data Updating */

        delay(3333)

        setForegroundAsync(ForegroundInfo(Foreground.NotificationId, notificationBuilder.create(notificationDone = true)))

        workerResult = Result.success()

        return workerResult
    }

}