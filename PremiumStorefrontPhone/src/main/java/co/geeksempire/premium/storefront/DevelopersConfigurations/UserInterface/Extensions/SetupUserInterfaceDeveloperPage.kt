/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/15/21, 9:07 AM
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

    setupDeveloperLogoDesign(themeType)

    setupDeveloperCountryFlagDesign(themeType)

    setupContactOptions(themeType)

}

fun DeveloperIntroductionPage.setupDeveloperLogoDesign(themeType: Boolean) {

    val developerLogoBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            getDrawable(R.drawable.developer_logo_background_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_logo_background_dark) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_logo_background_light) as LayerDrawable
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

            getDrawable(R.drawable.developer_logo_background_extended_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_logo_background_extended_dark) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_logo_background_extended_light) as LayerDrawable
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

fun DeveloperIntroductionPage.setupDeveloperCountryFlagDesign(themeType: Boolean) {

    val developerCountryFlagBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            getDrawable(R.drawable.developer_country_flag_background_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_country_flag_background_dark) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_country_flag_background_light) as LayerDrawable
    }

    val negativeSpaceTopLeft = floatArrayOf(
        0f,//topLeftCorner
        0f,//topLeftCorner

        51f,//topRightCorner
        51f,//topRightCorner

        0f,//bottomLeftCorner
        0f,//bottomLeftCorner

        0f,//bottomRightCorner
        0f//bottomRightCorner
    )

    val shapeNegativeSpaceTopLeft: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceTopLeft, null, null))

    shapeNegativeSpaceTopLeft.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerCountryFlagBackground.setDrawableByLayerId(R.id.clearTopLeft, shapeNegativeSpaceTopLeft)

    val negativeSpaceBottomRight = floatArrayOf(
        0f,//topLeftCorner
        0f,//topLeftCorner

        51f,//topRightCorner
        51f,//topRightCorner

        0f,//bottomLeftCorner
        0f,//bottomLeftCorner

        0f,//bottomRightCorner
        0f//bottomRightCorner
    )

    val shapeNegativeSpaceBottomRight: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceBottomRight, null, null))

    shapeNegativeSpaceBottomRight.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerCountryFlagBackground.setDrawableByLayerId(R.id.clearBottomRight, shapeNegativeSpaceBottomRight)

    val shapeNegativeSpaceBottomLeft: ShapeDrawable = ShapeDrawable(RoundRectShape(floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f), null, null))

    shapeNegativeSpaceBottomLeft.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerCountryFlagBackground.setDrawableByLayerId(R.id.clearBottomLeft, shapeNegativeSpaceBottomLeft)

    developerIntroductionLayoutBinding.developerCountryFlagBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    developerIntroductionLayoutBinding.developerCountryFlagBackground.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    developerIntroductionLayoutBinding.developerCountryFlagBackground.setImageDrawable(developerCountryFlagBackground)

}

fun DeveloperIntroductionPage.setupContactOptions(themeType: Boolean) {

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.setOnClickListener {

        developerIntroductionLayoutBinding.contactOptionsCircleMenu.open(true)

    }

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.setOnItemClickListener { buttonIndex ->



    }

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.onMenuOpenAnimationStart { }

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.onMenuOpenAnimationEnd { }

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.onMenuCloseAnimationStart { }

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.onMenuCloseAnimationEnd { }

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.onButtonClickAnimationStart { buttonIndex ->

    }

    developerIntroductionLayoutBinding.contactOptionsCircleMenu.onButtonClickAnimationEnd { buttonIndex ->

    }

}