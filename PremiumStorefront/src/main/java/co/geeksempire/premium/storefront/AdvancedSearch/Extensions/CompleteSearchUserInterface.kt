/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/18/21, 4:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.Extensions

import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R

fun CompleteSearch.setupCompleteSearchUserInterface(themeType: Boolean) {

    val shadowPaint = Paint()
    shadowPaint.color = getColor(R.color.red)

    completeSearchLayoutBinding.searchBackgroundCompleteSearch.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shadowPaint)
    shadowPaint.setShadowLayer(53f, 3f, 7f, getColor(R.color.red))
    completeSearchLayoutBinding.searchBackgroundCompleteSearch.setLayerPaint(shadowPaint)

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

            completeSearchLayoutBinding.brandingBackground.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_light))

            completeSearchLayoutBinding.searchBackgroundCompleteSearch.setImageDrawable(getDrawable(R.drawable.preferences_item_background_light))
            completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.dark_transparent))

            completeSearchLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

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

            completeSearchLayoutBinding.brandingBackground.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_dark))

            completeSearchLayoutBinding.searchBackgroundCompleteSearch.setImageDrawable(getDrawable(R.drawable.preferences_item_background_dark))
            completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.light_transparent))

            completeSearchLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_dark))

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

            completeSearchLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            completeSearchLayoutBinding.brandingBackground.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_light))

            completeSearchLayoutBinding.searchBackgroundCompleteSearch.setImageDrawable(getDrawable(R.drawable.preferences_item_background_light))
            completeSearchLayoutBinding.searchIconCompleteSearch.imageTintList = ColorStateList.valueOf(getColor(R.color.dark_transparent))

            completeSearchLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

        }
    }

}