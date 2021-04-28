/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/27/21 5:46 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Extensions

import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront


fun Storefront.setupUserInterface() {

    val r = 29f

    val shapeLightShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(floatArrayOf(r, r, r, r, r, r, r, r), null, null))
    shapeLightShadow.paint.apply {
        color = Color.WHITE

        setShadowLayer(27f, 0f, 0f, Color.WHITE)
    }

    val shapeDarkShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(floatArrayOf(r, r, r, r, r, r, r, r), null, null))
    shapeDarkShadow.paint.apply {
        color = Color.BLACK

        setShadowLayer(27f, 0f, 0f, Color.BLACK)
    }

    val shadowLayer = getDrawable(R.drawable.shadow_background) as LayerDrawable

    storefrontLayoutBinding.test.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeLightShadow.paint)
    storefrontLayoutBinding.test.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeDarkShadow.paint)

    shadowLayer.setDrawableByLayerId(R.id.lightShadow, shapeLightShadow)
    shadowLayer.setDrawableByLayerId(R.id.darkShadow, shapeDarkShadow)

    storefrontLayoutBinding.test.setImageDrawable(shadowLayer)

}