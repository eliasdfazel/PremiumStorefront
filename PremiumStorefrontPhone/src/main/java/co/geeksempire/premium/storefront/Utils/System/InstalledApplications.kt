/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/7/21, 7:05 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.System

import android.content.Context
import android.content.pm.PackageManager

class InstalledApplications (val context: Context) {

    fun appIsInstalled(packageName: String?): Boolean {

        val packageManager: PackageManager = context.packageManager

        return try {

            packageManager.getPackageInfo(packageName!!, 0)

            true

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()


            false
        } catch (e: Exception) {
            e.printStackTrace()

            false

        }
    }

}