/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/21/21, 3:05 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Colors

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import co.geeksempire.premium.storefront.Utils.UI.Images.drawableToBitmap

fun extractDominantColor(drawable: Drawable): Int {

    var dominantColor: Int = Color.rgb(35, 125, 174)

    val bitmap: Bitmap = drawableToBitmap(drawable)

    var currentColor: Palette
    try {
        if (bitmap != null && !bitmap.isRecycled) {
            currentColor = Palette.from(bitmap).generate()
            val defaultColor: Int = Color.rgb(35, 125, 174)
            dominantColor = currentColor.getDominantColor(defaultColor)
        }
    } catch (e: Exception) {
        e.printStackTrace()

        try {
            if (bitmap != null && !bitmap.isRecycled) {
                currentColor = Palette.from(bitmap).generate()
                val defaultColor: Int = Color.rgb(35, 125, 174)
                dominantColor = currentColor.getMutedColor(defaultColor)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }
    return dominantColor
}

fun extractVibrantColor(drawable: Drawable): Int {

    var vibrantColor: Int = Color.rgb(174, 35, 87)

    val bitmap: Bitmap = drawableToBitmap(drawable)

    var currentColor: Palette
    try {
        if (bitmap != null && !bitmap.isRecycled) {
            currentColor = Palette.from(bitmap).generate()
            val defaultColor: Int = Color.rgb(174, 35, 87)
            vibrantColor = currentColor.getVibrantColor(defaultColor)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        try {
            if (bitmap != null && !bitmap.isRecycled) {
                currentColor = Palette.from(bitmap).generate()
                val defaultColor: Int = Color.rgb(174, 35, 87)
                vibrantColor = currentColor.getMutedColor(defaultColor)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    return vibrantColor
}

fun extractMutedColor(drawable: Drawable): Int {

    var mutedColor: Int = Color.DKGRAY

    val bitmap: Bitmap = drawableToBitmap(drawable)

    var currentColor: Palette
    try {
        if (bitmap != null && !bitmap.isRecycled) {
            currentColor = Palette.from(bitmap).generate()
            val defaultColor: Int = Color.DKGRAY
            mutedColor = currentColor.getDarkMutedColor(defaultColor)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        try {
            if (bitmap != null && !bitmap.isRecycled) {
                currentColor = Palette.from(bitmap).generate()
                val defaultColor: Int = Color.DKGRAY
                mutedColor = currentColor.getMutedColor(defaultColor)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }
    return mutedColor
}

fun isColorDark(color: Int): Boolean {

    val darkness: Double = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255

    return (darkness >= 0.50)
}

fun isDrawableLightDark(drawable: Drawable): Boolean {

    var isLightDark = false

    val calculateLuminance = ColorUtils.calculateLuminance(extractDominantColor(drawable))

    if (calculateLuminance > 0.50) { //light
        isLightDark = true
    } else if (calculateLuminance <= 0.50) { //dark
        isLightDark = false
    }

    return isLightDark
}

fun isColorLightDark(inputColor: Int): Boolean {

    var isLightDark = false

    val calculateLuminance = ColorUtils.calculateLuminance(inputColor)

    if (calculateLuminance > 0.50) { //light
        isLightDark = true
    } else if (calculateLuminance <= 0.50) { //dark
        isLightDark = false
    }

    return isLightDark
}