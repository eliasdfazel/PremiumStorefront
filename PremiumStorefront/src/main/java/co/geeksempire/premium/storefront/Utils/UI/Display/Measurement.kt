/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/17/21, 6:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Display

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

fun columnCount(context: Context, itemWidth: Int): Int {

    var spanCount = (displayX(context) / convertDpToPixel(context, itemWidth.toFloat())).toInt()

    if (spanCount < 1) {
        spanCount = 1
    }

    return spanCount
}

fun rowCount(context: Context, itemHeight: Int): Int {

    var spanCount = (displayY(context) / convertDpToPixel(context, itemHeight.toFloat())).toInt()

    if (spanCount < 1) {
        spanCount = 1
    }

    return spanCount
}

fun columnCount(context: Context, widthOfRootView: Float, itemWidth: Int, marginHorizontal: Float = 0f): Int {

    var spanCount = ((widthOfRootView - marginHorizontal)/ convertDpToPixel(context, itemWidth.toFloat())).toInt()

    if (spanCount < 1) {
        spanCount = 1
    }

    return spanCount
}

fun convertDpToPixel(context: Context, dp: Float): Float {
    val resources: Resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertPixelToDp(context: Context, pixel: Float): Float {
    val resources: Resources = context.resources
    val metrics = resources.displayMetrics
    return pixel / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertDpToInteger(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

fun displayX(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

fun displayY(context: Context): Int {
    return context.resources.displayMetrics.heightPixels
}

fun statusBarHeight(context: Context) : Int {

    var statusBarHeight = 0

    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
    }

    return statusBarHeight
}

fun navigationBarHeight(context: Context) : Int {

    var navigationBarHeight = 0

    val resourceIdNavigationBar: Int = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceIdNavigationBar > 0) {
        navigationBarHeight = context.resources.getDimensionPixelSize(resourceIdNavigationBar)
    }

    return navigationBarHeight
}