/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/13/21, 12:39 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Extensions

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.DeveloperIntroductionPage
import co.geeksempire.premium.storefront.R

fun DeveloperIntroductionPage.setupUserInterfaceDeveloperPage(themeType: Boolean) {


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