/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 8:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.IO

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.nio.charset.Charset

class UpdatingDataIO (private val context: Context) {

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

}