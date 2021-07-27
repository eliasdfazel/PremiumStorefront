/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/23/21, 6:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.Extensions

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Animations.ShadowAnimation

fun CategoryDetails.setupCategoryDetailsUserInterface(themeType: Boolean = ThemeType.ThemeLight) {

    if (!BuildConfig.DEBUG) {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
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

            prepareActionCenterUserInterface.design(ThemeType.ThemeLight)

            categoryDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            categoryDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            categoryDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

            categoryDetailsLayoutBinding.categoryNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryNameTextView.setTextColor(getColor(R.color.dark))

            categoryDetailsLayoutBinding.categoryIconImageView.background = getDrawable(R.drawable.squircle_icon_light)
            categoryDetailsLayoutBinding.categoryIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            categoryDetailsLayoutBinding.uniqueRecyclerView.background = getDrawable(R.drawable.unique_section_background_light)

            categoryDetailsLayoutBinding.leftBlurView.setOverlayColor(getColor(R.color.premiumLightTransparent))
            categoryDetailsLayoutBinding.rightBlurView.setOverlayColor(getColor(R.color.premiumLightTransparent))

            categoryDetailsLayoutBinding.uniqueRecommendationTextView.background = getDrawable(R.drawable.unique_section_text_background_light)

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

            prepareActionCenterUserInterface.design(ThemeType.ThemeDark)

            categoryDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            categoryDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

            categoryDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_dark))

            categoryDetailsLayoutBinding.categoryNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.dark))
            categoryDetailsLayoutBinding.categoryNameTextView.setTextColor(getColor(R.color.light))

            categoryDetailsLayoutBinding.categoryIconImageView.background = getDrawable(R.drawable.squircle_icon_dark)
            categoryDetailsLayoutBinding.categoryIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.dark))
            categoryDetailsLayoutBinding.categoryIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

            categoryDetailsLayoutBinding.uniqueRecyclerView.background = getDrawable(R.drawable.unique_section_background_dark)

            categoryDetailsLayoutBinding.leftBlurView.setOverlayColor(getColor(R.color.premiumDarkTransparent))
            categoryDetailsLayoutBinding.rightBlurView.setOverlayColor(getColor(R.color.premiumDarkTransparent))

            categoryDetailsLayoutBinding.uniqueRecommendationTextView.background = getDrawable(R.drawable.unique_section_text_background_dark)

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

            prepareActionCenterUserInterface.design(ThemeType.ThemeLight)

            categoryDetailsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            categoryDetailsLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            categoryDetailsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

            categoryDetailsLayoutBinding.categoryNameTextView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryNameTextView.setTextColor(getColor(R.color.dark))

            categoryDetailsLayoutBinding.categoryIconImageView.background = getDrawable(R.drawable.squircle_icon_light)
            categoryDetailsLayoutBinding.categoryIconImageView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light))
            categoryDetailsLayoutBinding.categoryIconImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            categoryDetailsLayoutBinding.uniqueRecyclerView.background = getDrawable(R.drawable.unique_section_background_light)

            categoryDetailsLayoutBinding.leftBlurView.setOverlayColor(getColor(R.color.premiumLightTransparent))
            categoryDetailsLayoutBinding.rightBlurView.setOverlayColor(getColor(R.color.premiumLightTransparent))

            categoryDetailsLayoutBinding.uniqueRecommendationTextView.background = getDrawable(R.drawable.unique_section_text_background_light)

        }
    }

    ShadowAnimation().apply {

        textShadowValueAnimatorLoop(view = categoryDetailsLayoutBinding.uniqueRecommendationTextView,
            startValue = 0f, endValue = categoryDetailsLayoutBinding.uniqueRecommendationTextView.shadowRadius,
            startDuration = 1357, endDuration = 753,
            shadowColor = categoryDetailsLayoutBinding.uniqueRecommendationTextView.shadowColor, shadowX = categoryDetailsLayoutBinding.uniqueRecommendationTextView.shadowDx, shadowY = categoryDetailsLayoutBinding.uniqueRecommendationTextView.shadowDy,
            numberOfLoop = 29)

    }

}