/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 5:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.IO.Applications

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import co.geeksempire.premium.storefront.Utils.IO.DataUpdatingWork
import co.geeksempire.premium.storefront.Utils.IO.IO
import java.nio.charset.Charset

class ApplicationsDataIO (private val context: Context) {

    fun startUpdating() {

        val workRequest = OneTimeWorkRequestBuilder<DataUpdatingWork>()
            .setInputData(
                workDataOf(
                    IO.UpdateDataKey to IO.UpdateApplicationsDataKey.toByteArray(Charset.defaultCharset()),
                )
            )
            .addTag(DataUpdatingWork::class.java.simpleName)
            .build()

        val encryptionMigratingWorkManager = WorkManager.getInstance(context)
        encryptionMigratingWorkManager.enqueue(workRequest)

    }

}