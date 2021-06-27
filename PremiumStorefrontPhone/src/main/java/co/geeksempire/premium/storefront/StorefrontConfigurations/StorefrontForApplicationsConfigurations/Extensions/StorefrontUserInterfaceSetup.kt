/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/27/21, 11:36 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.Extensions

import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.AllContent.Adapter.AllContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.CategoryContent.Adapter.CategoriesAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.FeaturedContent.Adapter.FeaturedContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.NewContent.Adapter.NewContentAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun setupStorefrontUserInterface(context: AppCompatActivity,
                                 themePreferences: ThemePreferences,
                                 allContentAdapter: AllContentAdapter, featuredContentAdapter: FeaturedContentAdapter, newContentAdapter: NewContentAdapter,
                                 categoriesAdapter: CategoriesAdapter,
                                 rootView: View,
                                 allContentBackground: ImageView, brandingBackground: ImageView,
                                 profileView: ImageView, preferencesView: ImageView, favoritesView: ImageView,
                                 categoryIndicatorTextView: TextView,
                                 prepareActionCenterUserInterface: PrepareActionCenterUserInterface) {

    if (!BuildConfig.DEBUG) {
        context.window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    context.lifecycleScope.launch {

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

                    context.window.statusBarColor = context.getColor(R.color.premiumLight)
                    context.window.navigationBarColor = context.getColor(R.color.premiumLight)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                        context.window.insetsController?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

                    } else {

                        @Suppress("DEPRECATION")
                        context.window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        } else {
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        }

                    }

                    rootView.setBackgroundColor(context.getColor(R.color.premiumLight))

                    brandingBackground.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

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
                        color = context.getColor(R.color.dark)

                        setShadowLayer(31f, 0f, 0f, context.getColor(R.color.dark_transparent_high))
                    }

                    val shadowLayer = context.getDrawable(R.drawable.storefront_content_background_light) as LayerDrawable

                    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

                    allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
                    allContentBackground.background = (shadowLayer)
                    /* End - Add Shadow To Content Background */

                    prepareActionCenterUserInterface.let { centerUserInterface ->

                        centerUserInterface.design(ThemeType.ThemeLight)

                        centerUserInterface.setupIconsForStorefront(ThemeType.ThemeLight)

                    }

                    profileView.background = context.getDrawable(R.drawable.profile_icon_light)
                    preferencesView.setImageDrawable(context.getDrawable(R.drawable.preferences_icon_light))
                    favoritesView.background = context.getDrawable(R.drawable.squircle_background_light)

                    categoryIndicatorTextView.setTextColor(context.getColor(R.color.dark))

                }
                ThemeType.ThemeDark -> {

                    context.window.statusBarColor = context.getColor(R.color.premiumDark)
                    context.window.navigationBarColor = context.getColor(R.color.premiumDark)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                        context.window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

                    } else {

                        @Suppress("DEPRECATION")
                        context.window.decorView.systemUiVisibility = 0

                    }

                    rootView.setBackgroundColor(context.getColor(R.color.premiumDark))

                    brandingBackground.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

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
                        color = context.getColor(R.color.black)

                        setShadowLayer(31f, 0f, 0f, context.getColor(R.color.black_transparent))
                    }

                    val shadowLayer = context.getDrawable(R.drawable.storefront_content_background_dark) as LayerDrawable

                    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

                    allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
                    allContentBackground.background = (shadowLayer)
                    /* End - Add Shadow To Content Background */

                    prepareActionCenterUserInterface.let { centerUserInterface ->

                        centerUserInterface.design(ThemeType.ThemeDark)

                        centerUserInterface.setupIconsForStorefront(ThemeType.ThemeDark)

                    }

                    profileView.background = context.getDrawable(R.drawable.profile_icon_dark)
                    preferencesView.setImageDrawable(context.getDrawable(R.drawable.preferences_icon_dark))
                    favoritesView.background = context.getDrawable(R.drawable.squircle_background_dark)

                    categoryIndicatorTextView.setTextColor(context.getColor(R.color.light))

                }

            }

        }

    }

}