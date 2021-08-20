/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:56 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.Extensions

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface.GenreDetails
import co.geeksempire.premium.storefront.movies.R
import net.geeksempire.balloon.optionsmenu.library.Utils.statusBarHeight

fun GenreDetails.setupGenreDetailsUserInterface(themeType: Boolean) {

    if (!BuildConfig.DEBUG) {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    genreDetailsLayoutBinding.goBackView.apply {
        layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
            setMargins(marginStart, marginTop + statusBarHeight(applicationContext), marginEnd, marginBottom)
        }
    }

    genreDetailsLayoutBinding.genreIconImageView.apply {
        layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
            setMargins(marginStart, marginTop + statusBarHeight(applicationContext), marginEnd, marginBottom)
        }
    }

    when (themeType) {
        ThemeType.ThemeLight -> {

            window.statusBarColor = getColor(R.color.premiumLight)
            window.navigationBarColor = getColor(R.color.premiumLight)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }

            genreDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            genreDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            genreDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light_movie))

            genreDetailsLayoutBinding.genreNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            genreDetailsLayoutBinding.genreNameTextView.setTextColor(getColor(R.color.dark))

            genreDetailsLayoutBinding.genreIconImageView.background = getDrawable(R.drawable.squircle_icon_light_movie)
            genreDetailsLayoutBinding.genreIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            genreDetailsLayoutBinding.genreIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            genreDetailsLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumLight))
            genreDetailsLayoutBinding.blurryBackground.setSecondOverlayColor(getColor(R.color.premiumLightTransparent))

            genreDetailsLayoutBinding.movieNameBlurryView.setOverlayColor(getColor(R.color.light_transparent))

        }
        ThemeType.ThemeDark -> {

            window.statusBarColor = getColor(R.color.premiumDark)
            window.navigationBarColor = getColor(R.color.premiumDark)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = 0

            }

            genreDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            genreDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

            genreDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_dark_movie))

            genreDetailsLayoutBinding.genreNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.dark))
            genreDetailsLayoutBinding.genreNameTextView.setTextColor(getColor(R.color.light))

            genreDetailsLayoutBinding.genreIconImageView.background = getDrawable(R.drawable.squircle_icon_dark_movie)
            genreDetailsLayoutBinding.genreIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.dark))
            genreDetailsLayoutBinding.genreIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

            genreDetailsLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumDark))
            genreDetailsLayoutBinding.blurryBackground.setSecondOverlayColor(getColor(R.color.premiumDarkTransparent))

            genreDetailsLayoutBinding.movieNameBlurryView.setOverlayColor(getColor(R.color.dark_transparent))

        }
        else -> {

            window.statusBarColor = getColor(R.color.premiumLight)
            window.navigationBarColor = getColor(R.color.premiumLight)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }

            genreDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            genreDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            genreDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light_movie))

            genreDetailsLayoutBinding.genreNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            genreDetailsLayoutBinding.genreNameTextView.setTextColor(getColor(R.color.dark))

            genreDetailsLayoutBinding.genreIconImageView.background = getDrawable(R.drawable.squircle_icon_light_movie)
            genreDetailsLayoutBinding.genreIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            genreDetailsLayoutBinding.genreIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            genreDetailsLayoutBinding.blurryBackground.setOverlayColor(getColor(R.color.premiumLight))
            genreDetailsLayoutBinding.blurryBackground.setSecondOverlayColor(getColor(R.color.premiumLightTransparent))

            genreDetailsLayoutBinding.movieNameBlurryView.setOverlayColor(getColor(R.color.light_transparent))

        }
    }

}