/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/20/21, 5:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Colors

import android.graphics.Color
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

fun setColorAlpha(color: Int, alphaValue: Float /* 1 (Opaque) -- 255 (Transparent) */): Int {

    val alpha = (Color.alpha(color) * alphaValue).roundToInt()

    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)

    return Color.argb(alpha, red, green, blue)
}

fun colorsTheSame(firstColor: Int, secondColor: Int) : Boolean {

    val colorCheckpoint = (firstColor.absoluteValue - secondColor.absoluteValue).absoluteValue

    return (colorCheckpoint <= 5000)
}