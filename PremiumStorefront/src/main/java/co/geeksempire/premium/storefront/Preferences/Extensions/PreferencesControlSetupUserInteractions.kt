/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/30/21, 7:36 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.util.TypedValue
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.UserInterface.showBuiltInBrowser
import co.geeksempire.premium.storefront.Invitations.Send.SendInvitation
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Data.shareApplication
import co.geeksempire.premium.storefront.Utils.InApplicationReview.InApplicationReviewProcess
import co.geeksempire.premium.storefront.Utils.InApplicationReview.ReviewInterface
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.InApplicationUpdateProcess
import co.geeksempire.premium.storefront.Utils.InApplicationUpdate.UpdateResponse
import com.google.android.play.core.review.ReviewInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun PreferencesControl.preferencesControlSetupUserInteractions() {

    /*
     * Top Section
     */

    preferencesControlLayoutBinding.profileImageView.setOnClickListener {

        startActivity(
            Intent(applicationContext, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right, 0).toBundle())

    }

    preferencesControlLayoutBinding.profileNameView.setOnClickListener {

        startActivity(Intent(applicationContext, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right, 0).toBundle())

    }

    /*
     * Middle Section
     */

    preferencesControlLayoutBinding.whatNewView.setOnClickListener {

        showBuiltInBrowser(context = applicationContext,
            linkToLoad = getString(R.string.premiumStorefrontWhatsNewLink),
            gradientColorOne = getColor(R.color.default_color_game),
            gradientColorTwo = getColor(R.color.default_color_game_light))

    }

    preferencesControlLayoutBinding.supportView.setOnClickListener {

        showBuiltInBrowser(context = applicationContext,
            linkToLoad = getString(R.string.supportLink),
            gradientColorOne = getColor(R.color.default_color_game),
            gradientColorTwo = getColor(R.color.default_color_game_light))

    }

    preferencesControlLayoutBinding.submissionRequestView.setOnClickListener {

        showBuiltInBrowser(context = applicationContext,
            linkToLoad = getString(R.string.developersSubmissionRequest),
            gradientColorOne = getColor(R.color.default_color),
            gradientColorTwo = getColor(R.color.default_color_bright))

    }

    /*
     * Bottom Section
     */

    lifecycleScope.launch {

        reviewUtils.reviewSubmitted().collect {

            if (it) {

                preferencesControlLayoutBinding.rateItView.text = getString(R.string.followUsText)
                preferencesControlLayoutBinding.rateItView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33f)

            }

        }

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

        lifecycleScope.launch {

            reviewUtils.reviewSubmitted().collect {

                if (it) {

                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebookLink)))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                } else {

                    InApplicationReviewProcess(this@preferencesControlSetupUserInteractions)
                        .start(object : ReviewInterface {

                            override fun reviewSubmitted(reviewInfo: ReviewInfo) {
                                super.reviewSubmitted(reviewInfo)

                                preferencesControlLayoutBinding.rateItView.text = getString(R.string.followUsText)
                                preferencesControlLayoutBinding.rateItView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33f)

                            }

                        })

                }

            }

        }

    }

    preferencesControlLayoutBinding.shareItView.setOnClickListener {

        if (Firebase.auth.currentUser != null) {

            SendInvitation(this@preferencesControlSetupUserInteractions, preferencesControlLayoutBinding.rootView)
                .invite(Firebase.auth.currentUser)

        } else {

            shareApplication(context = this@preferencesControlSetupUserInteractions,
                aPackageName = packageName,
                applicationName = getString(R.string.applicationName), applicationSummary = getString(R.string.applicationSummary))

        }
    }

}