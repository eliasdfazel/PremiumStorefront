/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 8:00 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Write

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import java.nio.charset.Charset

class InputProcess (private val context: Context) {

    fun writeDataToFile(fileName: String, inputString: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val fileToWrite = context.openFileOutput(fileName, Context.MODE_PRIVATE)

        fileToWrite.write(inputString.toByteArray(Charset.defaultCharset()))

        fileToWrite.flush()
        fileToWrite.close()

    }

}