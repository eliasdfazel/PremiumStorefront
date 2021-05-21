/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/21/21, 8:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface

import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.R

fun ProductDetailsFragment.applyEffects() {

    /* Start - Add Shadow To Content Background */
    val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    backgroundShadowRadius[0] = (37).toFloat()//topLeftCorner
    backgroundShadowRadius[1] = (37).toFloat()//topLeftCorner

    backgroundShadowRadius[2] = (37).toFloat()//topRightCorner
    backgroundShadowRadius[3] = (37).toFloat()//topRightCorner

    backgroundShadowRadius[4] = (0).toFloat()//bottomRightCorner
    backgroundShadowRadius[5] = (0).toFloat()//bottomRightCorner

    backgroundShadowRadius[6] = (0).toFloat()//bottomLeftCorner
    backgroundShadowRadius[7] = (0).toFloat()//bottomLeftCorner

    val shapeShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
    shapeShadow.paint.apply {
        color = requireContext().getColor(R.color.dark)

        setShadowLayer(31f, 0f, 0f, requireContext().getColor(R.color.dark_transparent_high))
    }

    val shadowLayer = requireContext().getDrawable(R.drawable.product_details_background_light) as LayerDrawable

    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

    productDetailsLayoutBinding.allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
    productDetailsLayoutBinding.allContentBackground.background = (shadowLayer)
    /* End - Add Shadow To Content Background */

}