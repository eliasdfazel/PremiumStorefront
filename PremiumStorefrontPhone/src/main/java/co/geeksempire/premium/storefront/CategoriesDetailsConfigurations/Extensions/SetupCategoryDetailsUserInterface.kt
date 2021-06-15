/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 11:47 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.Extensions

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R

fun CategoryDetails.setupCategoryDetailsUserInterface(themeType: Boolean = ThemeType.ThemeLight) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            window.statusBarColor = getColor(R.color.premiumLight)
            window.navigationBarColor = getColor(R.color.premiumLight)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }

            categoryDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            categoryDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            categoryDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

            categoryDetailsLayoutBinding.categoryNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryNameTextView.setTextColor(getColor(R.color.dark))

            categoryDetailsLayoutBinding.categoryIconImageView.background = getDrawable(R.drawable.squircle_icon_light)
            categoryDetailsLayoutBinding.categoryIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

        }
        ThemeType.ThemeDark -> {

            window.statusBarColor = getColor(R.color.premiumDark)
            window.navigationBarColor = getColor(R.color.premiumDark)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = 0

            }

            categoryDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            categoryDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

            categoryDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_dark))

            categoryDetailsLayoutBinding.categoryNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.dark))
            categoryDetailsLayoutBinding.categoryNameTextView.setTextColor(getColor(R.color.light))

            categoryDetailsLayoutBinding.categoryIconImageView.background = getDrawable(R.drawable.squircle_icon_dark)
            categoryDetailsLayoutBinding.categoryIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.dark))
            categoryDetailsLayoutBinding.categoryIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

        }
        else -> {

            window.statusBarColor = getColor(R.color.premiumLight)
            window.navigationBarColor = getColor(R.color.premiumLight)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }

            categoryDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            categoryDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            categoryDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

            categoryDetailsLayoutBinding.categoryNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryNameTextView.setTextColor(getColor(R.color.dark))

            categoryDetailsLayoutBinding.categoryIconImageView.background = getDrawable(R.drawable.squircle_icon_light)
            categoryDetailsLayoutBinding.categoryIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

        }
    }

}