/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 4:07 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.Extensions

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R

fun CompleteSearch.setupCompleteSearchUserInterface(themeType: Boolean) {

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

            completeSearchLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            completeSearchLayoutBinding.cancelIconCompleteSearch.background = getDrawable(R.drawable.preferences_item_background_light)
            completeSearchLayoutBinding.cancelIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.dark_transparent))

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

            completeSearchLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            completeSearchLayoutBinding.cancelIconCompleteSearch.background = getDrawable(R.drawable.preferences_item_background_dark)
            completeSearchLayoutBinding.cancelIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.light_transparent))

        }
        else -> {



        }
    }

}