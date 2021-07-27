/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/27/21, 8:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.System

import android.content.Context
import android.content.pm.PackageManager

object Installed {
    const val InstalledApplicationsFile = "InstalledApplicationsFile"
}

class InstalledApplications (val context: Context) {

    fun appIsInstalled(packageName: String?): Boolean {

        val packageManager: PackageManager = context.packageManager

        return try {

            packageManager.getPackageInfo(packageName!!, 0)

            true

        } catch (e: PackageManager.NameNotFoundException) {


            false
        } catch (e: Exception) {

            false

        }
    }

}