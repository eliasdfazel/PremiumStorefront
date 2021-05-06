/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/6/21, 6:07 AM
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
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase

fun generateGooglePlayStoreDownloadLink(aPackageName: String) : String {

    return "https://play.google.com/store/apps/details?id=${aPackageName}" + "&rdid=${aPackageName}"
}

fun generateInstallDynamicLink(context: Context,
                               playStoreLink: String,
                               applicationName: String, applicationSummary: String,
                               mediatingSolution: String = context.packageName,
                               campaignName: String = context.packageName) : Uri {

    val dynamicLink = Firebase.dynamicLinks.dynamicLink { // or Firebase.dynamicLinks.shortLinkAsync
        link = Uri.parse(playStoreLink)
        domainUriPrefix = "https://premiumstorefront.page.link"
        androidParameters(context.packageName) {

        }
        googleAnalyticsParameters {
            source = context.getString(R.string.applicationName)
            medium = mediatingSolution
            campaign = campaignName
        }
        socialMetaTagParameters {
            title = applicationName
            description = applicationSummary
        }
    }

    return dynamicLink.uri
}

fun openPlayStoreToInstall(context: Context, packageName: String, applicationName: String, applicationSummary: String) {

    doVibrate(context, 159)

    context.startActivity(Intent(Intent.ACTION_VIEW,
        generateInstallDynamicLink(context, playStoreLink = generateGooglePlayStoreDownloadLink(packageName),
            applicationName = applicationName, applicationSummary = applicationSummary)
    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, R.anim.fade_out).toBundle())

}