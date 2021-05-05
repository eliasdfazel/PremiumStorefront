/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/4/21 10:58 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

fun Storefront.setupUserInterface() {

    /* Start - Add Shadow To Content Background */
    val backgroundDarkShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    backgroundDarkShadowRadius[0] = (29).toFloat()//topLeftCorner
    backgroundDarkShadowRadius[1] = (29).toFloat()//topLeftCorner

    backgroundDarkShadowRadius[2] = (29).toFloat()//topRightCorner
    backgroundDarkShadowRadius[3] = (29).toFloat()//topRightCorner

    backgroundDarkShadowRadius[4] = (29).toFloat()//bottomRightCorner
    backgroundDarkShadowRadius[5] = (29).toFloat()//bottomRightCorner

    backgroundDarkShadowRadius[6] = (29).toFloat()//bottomLeftCorner
    backgroundDarkShadowRadius[7] = (29).toFloat()//bottomLeftCorner

    val shapeDarkShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundDarkShadowRadius, null, null))
    shapeDarkShadow.paint.apply {
        color = getColor(R.color.dark)

        setShadowLayer(31f, 0f, 0f, getColor(R.color.dark_transparent_higher))
    }

    val shadowLayer = getDrawable(R.drawable.storefront_content_background_light) as LayerDrawable

    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeDarkShadow)

    storefrontLayoutBinding.contentWrapper.background = (shadowLayer)
    /* End - Add Shadow To Content Background */


    prepareActionCenterUserInterface.let {

        it.design()

        it.setupIconsForStorefront()

    }

}