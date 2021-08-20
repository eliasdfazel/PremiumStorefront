/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:53 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.Extensions

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesDetails
import co.geeksempire.premium.storefront.movies.R

fun MoviesDetails.applyNegativeSpaceEffectsForFavorite(themeType: Boolean) {

    val favoriteLayer = when (themeType) {
        ThemeType.ThemeLight -> {

            applicationContext.getDrawable(R.drawable.favorite_layer_light_movie) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            applicationContext.getDrawable(R.drawable.favorite_layer_dark_movie) as LayerDrawable

        }
        else -> applicationContext.getDrawable(R.drawable.favorite_layer_light_movie) as LayerDrawable
    }

    val negativeSpaceLeft = floatArrayOf(
        0f,//topLeftCorner
        0f,//topLeftCorner

        51f,//topRightCorner
        51f,//topRightCorner

        0f,//bottomLeftCorner
        0f,//bottomLeftCorner

        0f,//bottomRightCorner
        0f//bottomRightCorner
    )

    val shapeNegativeSpaceLeft: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceLeft, null, null))

    shapeNegativeSpaceLeft.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    val negativeSpaceRight = floatArrayOf(
        51f,//topLeftCorner
        51f,//topLeftCorner

        0f,//topRightCorner
        0f,//topRightCorner

        0f,//bottomLeftCorner
        0f,//bottomLeftCorner

        0f,//bottomRightCorner
        0f//bottomRightCorner
    )

    val shapeNegativeSpaceRight: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceRight, null, null))

    shapeNegativeSpaceRight.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    favoriteLayer.setDrawableByLayerId(R.id.leftClearLayer, shapeNegativeSpaceLeft)
    favoriteLayer.setDrawableByLayerId(R.id.rightClearLayer, shapeNegativeSpaceRight)

    moviesDetailsLayoutBinding.favoriteView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeNegativeSpaceLeft.paint)
    moviesDetailsLayoutBinding.favoriteView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    moviesDetailsLayoutBinding.favoriteView.background = (favoriteLayer)

}
