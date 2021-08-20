/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.FavoriteProducts
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.databinding.MoviesSectionsSwitcherLayoutBinding
import com.google.firebase.auth.FirebaseUser

fun storefrontMoviesUserInteractionSetup(context: AppCompatActivity, firebaseUser: FirebaseUser?, accountSelector: ActivityResultLauncher<Any?>,
                                   profileView: ImageView, preferencesView: ImageView, favoritesView: ImageView,
                                   moviesSectionsSwitcherLayoutBinding: MoviesSectionsSwitcherLayoutBinding, themeType: Boolean) {

    if (!BuildConfig.DEBUG) {
        context.window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    storefrontMoviesSectionSwitcher(context, moviesSectionsSwitcherLayoutBinding, themeType)

    profileView.setOnClickListener {

        if (firebaseUser == null) {

            accountSelector.launch(AccountSignIn.GoogleSignInRequestCode)

        } else {

            context.startActivity(Intent(context, AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right_movie, 0).toBundle())

        }

    }

    preferencesView.setOnClickListener {

        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_bounce_interpolator_movie)
        preferencesView.startAnimation(rotateAnimation)

        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                context.startActivity(Intent(context, PreferencesControl::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right_movie, 0).toBundle())

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

    }

    favoritesView.setOnClickListener {

        context.startActivity(
            Intent(context, FavoriteProducts::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(context, R.anim.slide_from_right_movie, 0).toBundle())

    }

}