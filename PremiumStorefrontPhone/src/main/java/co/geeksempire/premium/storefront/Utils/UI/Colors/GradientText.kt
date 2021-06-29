/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/29/21, 7:00 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Colors

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import android.widget.TextView

fun gradientText(textView: TextView,
                 gradientType: Int = Gradient.HorizontalGradient,
                 gradientColors: IntArray = intArrayOf(Color.RED, Color.MAGENTA),
                 gradientColorsPositions: FloatArray = floatArrayOf(0f, 1f)) {

    when (gradientType) {
        Gradient.HorizontalGradient -> {

            textView.apply {

                paint.shader = LinearGradient(
                    0f, 0f,
                    width.toFloat(), 0f,
                    gradientColors,
                    gradientColorsPositions,
                    Shader.TileMode.REPEAT
                )

            }

        }
        Gradient.VerticalGradient -> {

            textView.apply {

                paint.shader = LinearGradient(
                    0f, 0f,
                    0f, width.toFloat(),
                    gradientColors,
                    gradientColorsPositions,
                    Shader.TileMode.REPEAT
                )

            }

        }
        Gradient.RadialGradient -> {

            textView.apply {

                paint.shader = RadialGradient(
                    0f, 0f,
                    width.toFloat(),
                    gradientColors,
                    gradientColorsPositions,
                    Shader.TileMode.REPEAT)

            }

        }
    }

}