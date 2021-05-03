/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/2/21 11:44 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Data

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Notifications.doVibrate

fun generateGooglePlayStoreDownloadLink(aPackageName: String) : String {

    return "https://play.google.com/store/apps/details?id=${aPackageName}&rdid=${aPackageName}"
}

fun openPlayStoreToInstall(context: Context, packageName: String) {

    doVibrate(context, 159)

    context.startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse(generateGooglePlayStoreDownloadLink(packageName))
    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, R.anim.fade_out).toBundle())

}