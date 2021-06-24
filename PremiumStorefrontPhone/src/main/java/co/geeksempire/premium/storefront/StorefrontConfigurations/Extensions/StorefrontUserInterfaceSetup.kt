/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/24/21, 9:01 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun Storefront.setupStorefrontUserInterface() {

    if (!BuildConfig.DEBUG) {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    lifecycleScope.launch {

        themePreferences.checkThemeLightDark().collect {

            categoriesAdapter.apply {

                themeType = it

                if (storefrontCategories.isNotEmpty()) {

                    notifyItemRangeChanged(0, categoriesAdapter.itemCount)

                }

            }

            newContentAdapter.apply {

                themeType = it

                if (storefrontContents.isNotEmpty()) {

                    newContentAdapter.notifyItemRangeChanged(0, newContentAdapter.itemCount)
                }

            }

            allContentAdapter.apply {

                themeType = it

                if (storefrontContents.isNotEmpty()) {

                    allContentAdapter.notifyItemRangeChanged(0, allContentAdapter.itemCount)

                }

            }

            featuredContentAdapter.apply {

                themeType = it

                if (storefrontContents.isNotEmpty()) {

                    featuredContentAdapter.notifyItemRangeChanged(0, featuredContentAdapter.itemCount)

                }

            }

            when (it) {

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

                    storefrontLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

                    storefrontLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

                    /* Start - Add Shadow To Content Background */
                    val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

                    backgroundShadowRadius[0] = (29).toFloat()//topLeftCorner
                    backgroundShadowRadius[1] = (29).toFloat()//topLeftCorner

                    backgroundShadowRadius[2] = (13).toFloat()//topRightCorner
                    backgroundShadowRadius[3] = (13).toFloat()//topRightCorner

                    backgroundShadowRadius[4] = (13).toFloat()//bottomRightCorner
                    backgroundShadowRadius[5] = (13).toFloat()//bottomRightCorner

                    backgroundShadowRadius[6] = (29).toFloat()//bottomLeftCorner
                    backgroundShadowRadius[7] = (29).toFloat()//bottomLeftCorner

                    val shapeShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
                    shapeShadow.paint.apply {
                        color = getColor(R.color.dark)

                        setShadowLayer(31f, 0f, 0f, getColor(R.color.dark_transparent_high))
                    }

                    val shadowLayer = getDrawable(R.drawable.storefront_content_background_light) as LayerDrawable

                    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

                    storefrontLayoutBinding.allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
                    storefrontLayoutBinding.allContentBackground.background = (shadowLayer)
                    /* End - Add Shadow To Content Background */

                    prepareActionCenterUserInterface.let { centerUserInterface ->

                        centerUserInterface.design(ThemeType.ThemeLight)

                        centerUserInterface.setupIconsForStorefront(ThemeType.ThemeLight)

                    }

                    storefrontLayoutBinding.profileView.background = getDrawable(R.drawable.profile_icon_light)
                    storefrontLayoutBinding.preferencesView.setImageDrawable(getDrawable(R.drawable.preferences_icon_light))
                    storefrontLayoutBinding.favoritesView.background = getDrawable(R.drawable.squircle_background_light)

                    storefrontLayoutBinding.categoryIndicatorTextView.setTextColor(getColor(R.color.dark))

                    storefrontLayoutBinding.leftActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_dark))
                    storefrontLayoutBinding.middleActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_dark))
                    storefrontLayoutBinding.rightActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_dark))

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

                    storefrontLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

                    storefrontLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

                    /* Start - Add Shadow To Content Background */
                    val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

                    backgroundShadowRadius[0] = (29).toFloat()//topLeftCorner
                    backgroundShadowRadius[1] = (29).toFloat()//topLeftCorner

                    backgroundShadowRadius[2] = (13).toFloat()//topRightCorner
                    backgroundShadowRadius[3] = (13).toFloat()//topRightCorner

                    backgroundShadowRadius[4] = (13).toFloat()//bottomRightCorner
                    backgroundShadowRadius[5] = (13).toFloat()//bottomRightCorner

                    backgroundShadowRadius[6] = (29).toFloat()//bottomLeftCorner
                    backgroundShadowRadius[7] = (29).toFloat()//bottomLeftCorner

                    val shapeShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
                    shapeShadow.paint.apply {
                        color = getColor(R.color.black)

                        setShadowLayer(31f, 0f, 0f, getColor(R.color.black_transparent))
                    }

                    val shadowLayer = getDrawable(R.drawable.storefront_content_background_dark) as LayerDrawable

                    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

                    storefrontLayoutBinding.allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
                    storefrontLayoutBinding.allContentBackground.background = (shadowLayer)
                    /* End - Add Shadow To Content Background */

                    prepareActionCenterUserInterface.let { centerUserInterface ->

                        centerUserInterface.design(ThemeType.ThemeDark)

                        centerUserInterface.setupIconsForStorefront(ThemeType.ThemeDark)

                    }

                    storefrontLayoutBinding.profileView.background = getDrawable(R.drawable.profile_icon_dark)
                    storefrontLayoutBinding.preferencesView.setImageDrawable(getDrawable(R.drawable.preferences_icon_dark))
                    storefrontLayoutBinding.favoritesView.background = getDrawable(R.drawable.squircle_background_dark)

                    storefrontLayoutBinding.categoryIndicatorTextView.setTextColor(getColor(R.color.light))

                    storefrontLayoutBinding.leftActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_bright))
                    storefrontLayoutBinding.middleActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_bright))
                    storefrontLayoutBinding.rightActionView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_bright))

                }

            }

        }

    }

}