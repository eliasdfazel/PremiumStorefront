/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/5/21 1:00 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.widget.ImageView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

fun Storefront.setupUserInterface() {

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

    /* Start - Add Shadow To Content Background */
    val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    backgroundShadowRadius[0] = (29).toFloat()//topLeftCorner
    backgroundShadowRadius[1] = (29).toFloat()//topLeftCorner

    backgroundShadowRadius[2] = (29).toFloat()//topRightCorner
    backgroundShadowRadius[3] = (29).toFloat()//topRightCorner

    backgroundShadowRadius[4] = (29).toFloat()//bottomRightCorner
    backgroundShadowRadius[5] = (29).toFloat()//bottomRightCorner

    backgroundShadowRadius[6] = (29).toFloat()//bottomLeftCorner
    backgroundShadowRadius[7] = (29).toFloat()//bottomLeftCorner

    val shapShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
    shapShadow.paint.apply {
        color = getColor(R.color.dark)

        setShadowLayer(31f, 0f, 0f, getColor(R.color.dark_transparent_higher))
    }

    val shadowLayer = getDrawable(R.drawable.storefront_content_background_light) as LayerDrawable

    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapShadow)

    storefrontLayoutBinding.contentWrapper.setLayerType(ImageView.LAYER_TYPE_NONE, shapShadow.paint)
    storefrontLayoutBinding.contentWrapper.background = (shadowLayer)
    /* End - Add Shadow To Content Background */


    prepareActionCenterUserInterface.let {

        it.design()

        it.setupIconsForStorefront()

    }

}