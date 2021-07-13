/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/13/21, 1:31 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Extensions

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.DeveloperIntroductionPage
import co.geeksempire.premium.storefront.R

fun DeveloperIntroductionPage.setupUserInterfaceDeveloperPage(themeType: Boolean) {

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

            developerIntroductionLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            developerIntroductionLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

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

            developerIntroductionLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            developerIntroductionLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

        }
    }

    val developerLogoBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            getDrawable(R.drawable.developer_logo_background) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_logo_background) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_logo_background) as LayerDrawable
    }

    val shapeNegativeSpaceCircle: ShapeDrawable = ShapeDrawable(OvalShape())

    shapeNegativeSpaceCircle.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerLogoBackground.setDrawableByLayerId(R.id.circleLayer, shapeNegativeSpaceCircle)

    developerIntroductionLayoutBinding.logoBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeNegativeSpaceCircle.paint)
    developerIntroductionLayoutBinding.logoBackground.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    developerIntroductionLayoutBinding.logoBackground.setImageDrawable(developerLogoBackground)

    val developerLogoBackgroundExtended = when (themeType) {
        ThemeType.ThemeLight -> {

            getDrawable(R.drawable.developer_logo_background_extended) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_logo_background_extended) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_logo_background_extended) as LayerDrawable
    }

    val negativeSpaceRightExtended = floatArrayOf(
        0f,//topLeftCorner
        0f,//topLeftCorner

        0f,//topRightCorner
        0f,//topRightCorner

        51f,//bottomLeftCorner
        51f,//bottomLeftCorner

        0f,//bottomRightCorner
        0f//bottomRightCorner
    )

    val shapeNegativeSpaceRightExtended: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceRightExtended, null, null))

    shapeNegativeSpaceRightExtended.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerLogoBackgroundExtended.setDrawableByLayerId(R.id.clearLayer, shapeNegativeSpaceRightExtended)

    developerIntroductionLayoutBinding.logoBackgroundExtended.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeNegativeSpaceRightExtended.paint)
    developerIntroductionLayoutBinding.logoBackgroundExtended.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    developerIntroductionLayoutBinding.logoBackgroundExtended.setImageDrawable(developerLogoBackgroundExtended)

}