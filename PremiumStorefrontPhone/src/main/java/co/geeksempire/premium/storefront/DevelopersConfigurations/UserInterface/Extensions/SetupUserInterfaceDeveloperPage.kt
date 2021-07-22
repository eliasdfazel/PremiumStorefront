/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/22/21, 8:15 AM
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
import android.os.Handler
import android.os.Looper
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

            developerIntroductionLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            developerIntroductionLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            developerIntroductionLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

            developerIntroductionLayoutBinding.logoBackgroundColor.setBackgroundColor(getColor(R.color.premiumLight))

            developerIntroductionLayoutBinding.contentBlurView.setOverlayColor(getColor(R.color.premiumLightTransparent))
            developerIntroductionLayoutBinding.contentBlurView.setSecondOverlayColor(getColor(R.color.premiumLight))

            developerIntroductionLayoutBinding.developerDescriptionTextView.setTextColor(getColor(R.color.dark))

            developerIntroductionLayoutBinding.productApplications.background = getDrawable(R.drawable.developer_products_background_light)
            developerIntroductionLayoutBinding.productGames.background = getDrawable(R.drawable.developer_products_background_light)
            developerIntroductionLayoutBinding.productBooks.background = getDrawable(R.drawable.developer_products_background_light)
            developerIntroductionLayoutBinding.productMovies.background = getDrawable(R.drawable.developer_products_background_light)

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

            developerIntroductionLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            developerIntroductionLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

            developerIntroductionLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_dark))

            developerIntroductionLayoutBinding.logoBackgroundColor.setBackgroundColor(getColor(R.color.premiumDark))

            developerIntroductionLayoutBinding.contentBlurView.setOverlayColor(getColor(R.color.premiumDarkTransparent))
            developerIntroductionLayoutBinding.contentBlurView.setSecondOverlayColor(getColor(R.color.premiumDark))

            developerIntroductionLayoutBinding.developerDescriptionTextView.setTextColor(getColor(R.color.light))

            developerIntroductionLayoutBinding.productApplications.background = getDrawable(R.drawable.developer_products_background_dark)
            developerIntroductionLayoutBinding.productGames.background = getDrawable(R.drawable.developer_products_background_dark)
            developerIntroductionLayoutBinding.productBooks.background = getDrawable(R.drawable.developer_products_background_dark)
            developerIntroductionLayoutBinding.productMovies.background = getDrawable(R.drawable.developer_products_background_dark)

        }
    }

    setupDeveloperLogoDesign(themeType)

    setupDeveloperCountryFlagDesign(themeType)

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

    Handler(Looper.getMainLooper()).postDelayed({

        setupContactOptions(themeType)

    }, 512)

}

fun DeveloperIntroductionPage.setupContactOptions(themeType: Boolean) {

    setupDeveloperWebsiteDesign(themeType)

    setupDeveloperEmailDesign(themeType)

    setupDeveloperSocialMediaDesign(themeType)

}

fun DeveloperIntroductionPage.setupDeveloperWebsiteDesign(themeType: Boolean) {

    developerIntroductionLayoutBinding.developerWebsiteImageView.visibility = View.VISIBLE

    val developerWebsiteBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            getDrawable(R.drawable.developer_website_background_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_website_background_dark) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_website_background_light) as LayerDrawable
    }

    val shapeNegativeSpaceCircle: ShapeDrawable = ShapeDrawable(OvalShape())

    shapeNegativeSpaceCircle.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerWebsiteBackground.setDrawableByLayerId(R.id.clearCircle, shapeNegativeSpaceCircle)

    val shapeNegativeSpaceRectangle: ShapeDrawable = ShapeDrawable(RoundRectShape(floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f), null, null))

    shapeNegativeSpaceRectangle.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerWebsiteBackground.setDrawableByLayerId(R.id.clearRectangle, shapeNegativeSpaceRectangle)

    developerIntroductionLayoutBinding.developerWebsiteImageView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    developerIntroductionLayoutBinding.developerWebsiteImageView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    developerIntroductionLayoutBinding.developerWebsiteImageView.background = developerWebsiteBackground

}

fun DeveloperIntroductionPage.setupDeveloperEmailDesign(themeType: Boolean) {

    developerIntroductionLayoutBinding.developerEmailImageView.visibility = View.VISIBLE

    val developerEmailBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            getDrawable(R.drawable.developer_email_background_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_email_background_dark) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_email_background_light) as LayerDrawable
    }

    val shapeNegativeSpaceCircle: ShapeDrawable = ShapeDrawable(OvalShape())

    shapeNegativeSpaceCircle.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerEmailBackground.setDrawableByLayerId(R.id.clearCircle, shapeNegativeSpaceCircle)

    val shapeNegativeSpaceRectangle: ShapeDrawable = ShapeDrawable(RoundRectShape(floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f), null, null))

    shapeNegativeSpaceRectangle.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerEmailBackground.setDrawableByLayerId(R.id.clearRectangle, shapeNegativeSpaceRectangle)

    developerIntroductionLayoutBinding.developerEmailImageView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    developerIntroductionLayoutBinding.developerEmailImageView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    developerIntroductionLayoutBinding.developerEmailImageView.background = developerEmailBackground

}

fun DeveloperIntroductionPage.setupDeveloperSocialMediaDesign(themeType: Boolean) {

    developerIntroductionLayoutBinding.developerSocialMediaImageView.visibility = View.VISIBLE

    val developerSocialMediaBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            getDrawable(R.drawable.developer_social_media_background_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            getDrawable(R.drawable.developer_social_media_background_dark) as LayerDrawable

        }
        else -> getDrawable(R.drawable.developer_social_media_background_light) as LayerDrawable
    }

    val shapeNegativeSpaceCircle: ShapeDrawable = ShapeDrawable(OvalShape())

    shapeNegativeSpaceCircle.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerSocialMediaBackground.setDrawableByLayerId(R.id.clearCircle, shapeNegativeSpaceCircle)

    val shapeNegativeSpaceRectangle: ShapeDrawable = ShapeDrawable(RoundRectShape(floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f), null, null))

    shapeNegativeSpaceRectangle.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    developerSocialMediaBackground.setDrawableByLayerId(R.id.clearRectangle, shapeNegativeSpaceRectangle)

    developerIntroductionLayoutBinding.developerSocialMediaImageView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    developerIntroductionLayoutBinding.developerSocialMediaImageView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    developerIntroductionLayoutBinding.developerSocialMediaImageView.background = developerSocialMediaBackground

}