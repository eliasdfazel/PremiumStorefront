/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 10:05 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.BuiltInBrowser
import co.geeksempire.premium.storefront.Invitations.Send.SendInvitation
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Data.shareApplication
import co.geeksempire.premium.storefront.Utils.InApplicationReview.InApplicationReviewProcess
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.InApplicationUpdateProcess
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.UpdateResponse

fun PreferencesControl.preferencesControlSetupUserInteractions() {

    preferencesControlLayoutBinding.profileImageView.setOnClickListener {

        startActivity(
            Intent(applicationContext, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right, R.anim.slide_out_left).toBundle())

    }

    preferencesControlLayoutBinding.profileNameView.setOnClickListener {

        startActivity(Intent(applicationContext, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right, R.anim.slide_out_left).toBundle())

    }

    preferencesControlLayoutBinding.whatNewView.setOnClickListener {

        BuiltInBrowser.show(context = applicationContext,
            linkToLoad = getString(R.string.premiumStorefrontWhatsNewLink),
            gradientColorOne = getColor(R.color.default_color_game),
            gradientColorTwo = getColor(R.color.default_color_game_light))

    }

    preferencesControlLayoutBinding.supportView.setOnClickListener {

        BuiltInBrowser.show(context = applicationContext,
            linkToLoad = getString(R.string.supportLink),
            gradientColorOne = getColor(R.color.default_color_game),
            gradientColorTwo = getColor(R.color.default_color_game_light))

    }

    preferencesControlLayoutBinding.updateItView.setOnClickListener {

        InApplicationUpdateProcess(this@preferencesControlSetupUserInteractions, preferencesControlLayoutBinding.rootView)
            .initialize(object : UpdateResponse {

                override fun newUpdateAvailable() {
                    super.newUpdateAvailable()

                    preferencesControlLayoutBinding.updateItView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.yellow))

                }

                override fun latestVersionAlreadyInstalled() {
                    super.latestVersionAlreadyInstalled()

                    preferencesControlLayoutBinding.updateItView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.green))

                }

            })

    }

    preferencesControlLayoutBinding.rateItView.setOnClickListener {

        InApplicationReviewProcess(this@preferencesControlSetupUserInteractions)
            .start()

    }

    preferencesControlLayoutBinding.shareItView.setOnClickListener {

        if (firebaseUser != null) {

            SendInvitation(this@preferencesControlSetupUserInteractions, preferencesControlLayoutBinding.rootView)
                .invite(firebaseUser)

        } else {

            shareApplication(context = applicationContext,
                aPackageName = packageName,
                applicationName = getString(R.string.applicationName), applicationSummary = getString(R.string.applicationSummary))

        }
    }

}