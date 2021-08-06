/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/6/21, 11:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.Utils.Data

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Data.generateHashTag
import co.geeksempire.premium.storefront.Utils.Notifications.doVibrate
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataKey
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase

private fun generateGooglePlayStoreMovies(movieId: String) : String {

    return "https://play.google.com/store/movies/details?id=${movieId}"
}

private fun generateInstallDynamicMoviesLink(context: Context,
                                             aPackageName: String,
                                             applicationName: String, applicationSummary: String,
                                             mediatingSolution: String = context.packageName,
                                             campaignName: String = context.packageName) : Uri {

    val playStoreLink: String = generateGooglePlayStoreMovies(aPackageName)

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

private fun prepareInstallShortDynamicMoviesLink(context: Context,
                                                 aPackageName: String,
                                                 applicationName: String, applicationSummary: String,
                                                 mediatingSolution: String = context.packageName,
                                                 campaignName: String = context.packageName) : Task<ShortDynamicLink> {

    return Firebase.dynamicLinks.shortLinkAsync {
        link = Uri.parse(generateGooglePlayStoreMovies(aPackageName))
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

fun openPlayStoreToWatchMovie(context: Context, movieId: String, movieName: String, movieSummary: String) {

    Firebase.analytics.logEvent(Firebase.auth.currentUser?.displayName?:"Unknown", Bundle().apply {
        putString(MoviesDataKey.MovieId, movieId)
        putString(MoviesDataKey.MovieName, movieName)
    })

    doVibrate(context, 159)

    context.startActivity(Intent(Intent.ACTION_VIEW,
        generateInstallDynamicMoviesLink(context = context, aPackageName = movieId,
            applicationName = movieName, applicationSummary = movieSummary)
    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0).toBundle())

}

fun shareMovie(context: Context, movieId: String, movieName: String, movieSummary: String) {

    Firebase.analytics.logEvent(Firebase.auth.currentUser?.displayName?:"Unknown", Bundle().apply {
        putString(MoviesDataKey.MovieId, movieId)
        putString(MoviesDataKey.MovieName, movieName)
    })

    doVibrate(context, 159)

    prepareInstallShortDynamicMoviesLink(context, movieId, movieName, movieSummary)
        .addOnSuccessListener { shortDynamicLink ->

            val textToShare = Html.fromHtml(movieName, Html.FROM_HTML_MODE_COMPACT).toString() + "\n" +
                    Html.fromHtml(movieSummary, Html.FROM_HTML_MODE_COMPACT).toString() + "\n" +
                    shortDynamicLink.shortLink.toString() + " | " + generateHashTag(Html.fromHtml(movieName, Html.FROM_HTML_MODE_COMPACT).toString()) +
                    generateHashTag(Html.fromHtml(movieSummary, Html.FROM_HTML_MODE_COMPACT).toString())

            context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND, shortDynamicLink.shortLink).apply {
                putExtra(Intent.EXTRA_TEXT, textToShare)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                type = "text/plain"
            }, "Share $movieName"))

        }

}

