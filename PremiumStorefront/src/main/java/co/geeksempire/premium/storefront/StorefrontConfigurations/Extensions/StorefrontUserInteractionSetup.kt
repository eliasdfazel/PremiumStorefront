/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/9/21, 2:13 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.FavoriteProducts
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.databinding.SectionsSwitcherLayoutBinding
import com.google.firebase.auth.FirebaseUser

fun storefrontUserInteractionSetup(context: AppCompatActivity, rootView: ViewGroup,
                                   firebaseUser: FirebaseUser?, accountSelector: ActivityResultLauncher<Any?>,
                                   profileView: ImageView, preferencesView: ImageView, favoritesView: ImageView,
                                   sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding, themeType: Boolean) {

    storefrontSectionSwitcher(context, rootView, sectionsSwitcherLayoutBinding, themeType)

    profileView.setOnClickListener {

        if (firebaseUser == null) {

            accountSelector.launch(AccountSignIn.GoogleSignInRequestCode)

        } else {

            context.startActivity(Intent(context, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

        }

    }

    preferencesView.setOnClickListener {

        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_bounce_interpolator)
        preferencesView.startAnimation(rotateAnimation)

        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                context.startActivity(Intent(context, PreferencesControl::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, 0).toBundle())

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

    }

    favoritesView.setOnClickListener {

        context.startActivity(
            Intent(context, FavoriteProducts::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right, 0).toBundle())

    }

}