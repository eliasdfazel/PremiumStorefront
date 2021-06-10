/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 7:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Extensions

import android.app.ActivityOptions
import android.content.Intent
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.BuiltInBrowserConfigurations.BuiltInBrowser
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.R

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

}