/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/12/21, 10:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.Extensions

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails
import net.geeksempire.balloon.optionsmenu.library.Utils.navigationBarHeight
import net.geeksempire.balloon.optionsmenu.library.Utils.statusBarHeight

fun MoviesDetails.setupMoviesDetailsUserInterface(themeType: Boolean) {

    if (!BuildConfig.DEBUG) {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    moviesDetailsLayoutBinding.actionCenterView.layoutParams = (moviesDetailsLayoutBinding.actionCenterView.layoutParams as ConstraintLayout.LayoutParams).apply {
        setMargins(0, 0, 0, moviesDetailsLayoutBinding.actionCenterView.marginBottom + navigationBarHeight(applicationContext))
    }

    moviesDetailsLayoutBinding.goBackView.layoutParams = (moviesDetailsLayoutBinding.goBackView.layoutParams as ConstraintLayout.LayoutParams).apply {
        setMargins(moviesDetailsLayoutBinding.goBackView.marginStart, moviesDetailsLayoutBinding.goBackView.marginTop + statusBarHeight(applicationContext), 0, 0)
    }

    applyNegativeSpaceEffectsForFavorite(themeType)

    moviesDetailsLayoutBinding.favoriteView.layoutParams = (moviesDetailsLayoutBinding.favoriteView.layoutParams as ConstraintLayout.LayoutParams).apply {
        setMargins(0, moviesDetailsLayoutBinding.favoriteView.marginTop + statusBarHeight(applicationContext), moviesDetailsLayoutBinding.favoriteView.marginEnd, 0)
    }

    when (themeType) {
        ThemeType.ThemeLight -> {

            prepareActionCenterUserInterface.let { centerUserInterface ->

                centerUserInterface.design(ThemeType.ThemeLight)

                centerUserInterface.setupIconsForDetails(ThemeType.ThemeLight)

            }

            window.statusBarColor = getColor(R.color.premiumLight)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }

            moviesDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            moviesDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

        }
        ThemeType.ThemeDark -> {

            prepareActionCenterUserInterface.let { centerUserInterface ->

                centerUserInterface.design(ThemeType.ThemeLight)

                centerUserInterface.setupIconsForDetails(ThemeType.ThemeLight)

            }

            window.statusBarColor = getColor(R.color.premiumDark)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = 0

            }

            moviesDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            moviesDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

        }
    }

}