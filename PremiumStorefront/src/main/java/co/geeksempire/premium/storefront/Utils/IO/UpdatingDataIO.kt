/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/9/21, 9:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.IO

import android.content.Context
import androidx.work.*
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class UpdatingDataIO (private val context: Context) {

    /*
     * Applications
     */
    fun startUpdatingApplicationsData() {

        val workRequest = OneTimeWorkRequestBuilder<DataUpdatingWork>()
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateApplicationsDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateApplicationsDataKey)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueue(workRequest)

    }

    fun startUpdatingApplicationsDataPeriodic() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DataUpdatingWork>(13, TimeUnit.DAYS)
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateApplicationsDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateApplicationsDataKey)
            .setConstraints(constraints)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueueUniquePeriodicWork(IO.UpdateApplicationsDataKey, ExistingPeriodicWorkPolicy.REPLACE, workRequest)

    }

    /*
     * Games
     */
    fun startUpdatingGamesData() {

        val workRequest = OneTimeWorkRequestBuilder<DataUpdatingWork>()
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateGamesDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateGamesDataKey)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueue(workRequest)

    }

    fun startUpdatingGamesDataPeriodic() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DataUpdatingWork>(13, TimeUnit.DAYS)
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateGamesDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateGamesDataKey)
            .setConstraints(constraints)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueueUniquePeriodicWork(IO.UpdateGamesDataKey, ExistingPeriodicWorkPolicy.REPLACE, workRequest)

    }

    /*
     * Books
     */
    fun startUpdatingBooksData() {

        val workRequest = OneTimeWorkRequestBuilder<DataUpdatingWork>()
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateBooksDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateBooksDataKey)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueue(workRequest)

    }

    fun startUpdatingBooksDataPeriodic() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DataUpdatingWork>(13, TimeUnit.DAYS)
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateBooksDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateBooksDataKey)
            .setConstraints(constraints)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueueUniquePeriodicWork(IO.UpdateBooksDataKey, ExistingPeriodicWorkPolicy.REPLACE, workRequest)

    }

    /*
     * Movies
     */
    fun startUpdatingMoviesData() {

        val workRequest = OneTimeWorkRequestBuilder<DataUpdatingWork>()
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateMoviesDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateMoviesDataKey)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueue(workRequest)

    }

    fun startUpdatingMoviesDataPeriodic() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DataUpdatingWork>(13, TimeUnit.DAYS)
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateMoviesDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(IO.UpdateMoviesDataKey)
            .setConstraints(constraints)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueueUniquePeriodicWork(IO.UpdateMoviesDataKey, ExistingPeriodicWorkPolicy.REPLACE, workRequest)

    }

}