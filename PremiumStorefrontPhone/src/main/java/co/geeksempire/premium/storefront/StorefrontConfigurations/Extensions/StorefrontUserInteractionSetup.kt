/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/26/21, 5:45 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.FavoriteProducts
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

fun Storefront.userInteractionSetup() {

    storefrontLayoutBinding.profileView.setOnClickListener {

        if (firebaseUser == null) {

            accountSelector.launch(AccountSignIn.GoogleSignInRequestCode)

        } else {

            startActivity(Intent(applicationContext, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right, 0).toBundle())

        }

    }

    storefrontLayoutBinding.preferencesView.setOnClickListener {

        val rotateAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_bounce_interpolator)
        storefrontLayoutBinding.preferencesView.startAnimation(rotateAnimation)

        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                startActivity(Intent(applicationContext, PreferencesControl::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_in_right, 0).toBundle())

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

    }

    storefrontLayoutBinding.favoritesView.setOnClickListener {

        startActivity(
            Intent(this@userInteractionSetup, FavoriteProducts::class.java),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_from_right, 0).toBundle())

    }

}