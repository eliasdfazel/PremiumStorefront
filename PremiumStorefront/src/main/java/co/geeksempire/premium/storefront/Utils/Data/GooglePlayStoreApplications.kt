/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/6/21, 10:29 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Data

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataKey
import co.geeksempire.premium.storefront.Utils.Notifications.doVibrate
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase

private fun generateGooglePlayStoreApplications(aPackageName: String) : String {

    return "https://play.google.com/store/apps/details?id=${aPackageName}" + "&rdid=${aPackageName}"
}

private fun generateInstallDynamicApplicationsLink(context: Context,
                                                   aPackageName: String,
                                                   applicationName: String, applicationSummary: String,
                                                   mediatingSolution: String = context.packageName,
                                                   campaignName: String = context.packageName) : Uri {

    val playStoreLink: String = generateGooglePlayStoreApplications(aPackageName)

    val dynamicLink = Firebase.dynamicLinks.dynamicLink {
        link = Uri.parse(playStoreLink)
        domainUriPrefix = "https://premiumstorefront.page.link"
        androidParameters(aPackageName) {

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

private fun prepareInstallShortDynamicApplicationsLink(context: Context,
                                                       aPackageName: String,
                                                       applicationName: String, applicationSummary: String,
                                                       mediatingSolution: String = context.packageName,
                                                       campaignName: String = context.packageName) : Task<ShortDynamicLink> {

    return Firebase.dynamicLinks.shortLinkAsync {
        link = Uri.parse(generateGooglePlayStoreApplications(aPackageName))
        domainUriPrefix = "https://premiumstorefront.page.link"
        androidParameters(aPackageName) {

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
}

fun openPlayStoreToInstallApplications(context: Context, aPackageName: String, applicationName: String, applicationSummary: String) {

    Firebase.analytics.logEvent(Firebase.auth.currentUser?.displayName?:"Unknown", Bundle().apply {
        putString(ProductDataKey.ProductPackageName, aPackageName)
        putString(ProductDataKey.ProductName, applicationName)
    })

    doVibrate(context, 159)

    context.startActivity(Intent(Intent.ACTION_VIEW,
        generateInstallDynamicApplicationsLink(context = context, aPackageName = aPackageName,
            applicationName = applicationName, applicationSummary = applicationSummary)
    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0).toBundle())

}

fun shareApplication(context: Context, aPackageName: String, applicationName: String, applicationSummary: String) {

    Firebase.analytics.logEvent(Firebase.auth.currentUser?.displayName?:"Unknown", Bundle().apply {
        putString(ProductDataKey.ProductPackageName, aPackageName)
        putString(ProductDataKey.ProductName, applicationName)
    })

    doVibrate(context, 159)

    prepareInstallShortDynamicApplicationsLink(context, aPackageName, applicationName, applicationSummary)
        .addOnSuccessListener { shortDynamicLink ->

            val textToShare = Html.fromHtml(applicationName, Html.FROM_HTML_MODE_COMPACT).toString() + "\n" +
                    Html.fromHtml(applicationSummary, Html.FROM_HTML_MODE_COMPACT).toString() + "\n" +
                    shortDynamicLink.shortLink.toString() + " | " + generateHashTag(Html.fromHtml(applicationName, Html.FROM_HTML_MODE_COMPACT).toString()) +
                    generateHashTag(Html.fromHtml(applicationSummary, Html.FROM_HTML_MODE_COMPACT).toString())

            context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND, shortDynamicLink.shortLink).apply {
                putExtra(Intent.EXTRA_TEXT, textToShare)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                type = "text/plain"
            }, "Share $applicationName"))

        }

}

