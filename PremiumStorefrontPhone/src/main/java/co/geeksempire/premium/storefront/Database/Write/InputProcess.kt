/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 5:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Write

import java.io.File
import java.nio.charset.Charset

class InputProcess {

    fun writeDataToFile(fileName: String,inputString: String) {

        File(fileName).writeText(inputString, Charset.defaultCharset())

    }

}