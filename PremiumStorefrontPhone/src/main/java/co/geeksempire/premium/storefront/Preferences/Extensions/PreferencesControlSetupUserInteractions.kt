/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 6:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.R

fun PreferencesControl.preferencesControlSetupUserInteractions() {

    preferencesControlLayoutBinding.profileImageView.setOnClickListener {

        if (firebaseUser != null) {

        } else {

        }

    }

    preferencesControlLayoutBinding.profileNameView.setOnClickListener {

        if (firebaseUser != null) {

        } else {

        }

    }

    preferencesControlLayoutBinding.whatNewView.setOnClickListener {

        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.premiumStorefrontWhatsNewLink))).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, R.anim.fade_out).toBundle())

    }

    preferencesControlLayoutBinding.supportView.setOnClickListener {

        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.supportLink))).apply {
           flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, R.anim.fade_out).toBundle())

    }

}