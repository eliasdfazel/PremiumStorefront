/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/7/21, 8:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.Utils.UI.Drawable

import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape

fun applyShadowEffectRectangle(shadowDrawable: LayerDrawable,
                               shadowLayerId: Int,
                               shadowColor: Int,
                               shadowRadius: Float = 29f,
                               shadowDx: Float = 0f,
                               shadowDy: Float = 0f,
                               topLeftCorner: Int,
                               topRightCorner: Int,
                               bottomRightCorner: Int,
                               bottomLeftCorner: Int) : LayerDrawable {

    val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    backgroundShadowRadius[0] = (topLeftCorner).toFloat()//topLeftCorner
    backgroundShadowRadius[1] = (topLeftCorner).toFloat()//topLeftCorner

    backgroundShadowRadius[2] = (topRightCorner).toFloat()//topRightCorner
    backgroundShadowRadius[3] = (topRightCorner).toFloat()//topRightCorner

    backgroundShadowRadius[4] = (bottomRightCorner).toFloat()//bottomRightCorner
    backgroundShadowRadius[5] = (bottomRightCorner).toFloat()//bottomRightCorner

    backgroundShadowRadius[6] = (bottomLeftCorner).toFloat()//bottomLeftCorner
    backgroundShadowRadius[7] = (bottomLeftCorner).toFloat()//bottomLeftCorner

    val shadowShape: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
    shadowShape.paint.apply {
        color = shadowColor

        setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
    }

    shadowDrawable.setDrawableByLayerId(shadowLayerId, shadowShape)

    return shadowDrawable
}