/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 11:00 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.UI.Drawable

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape

fun applyClearEffectRectangle(negativeSpaceDrawable: LayerDrawable,
                              negativeSpaceLayerId: Int,
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

    val negativeSpace: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
    negativeSpace.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    negativeSpaceDrawable.setDrawableByLayerId(negativeSpaceLayerId, negativeSpace)

    return negativeSpaceDrawable
}