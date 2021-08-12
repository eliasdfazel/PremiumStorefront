/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/12/21, 11:44 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UI

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.Adapter.MovieDetailsPagerAdapter
import co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder.MovieDetailsViewHolder
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.Utils.UI.Drawable.applyClearEffectRectangle

fun MovieDetailsPagerAdapter.designOptionsMoviesBackground(moviesDetailsItemBinding: MovieDetailsViewHolder, themeType: Boolean) : LayerDrawable {

    var optionsBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            context.getDrawable(R.drawable.movie_details_rating_glowing_frame_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            context.getDrawable(R.drawable.movie_details_rating_glowing_frame_dark) as LayerDrawable

        }
        else -> context.getDrawable(R.drawable.movie_details_rating_glowing_frame_light) as LayerDrawable
    }

    optionsBackground = applyClearEffectRectangle(negativeSpaceDrawable = optionsBackground, negativeSpaceLayerId = R.id.clearLayer,
        topLeftCorner = 57,
        topRightCorner = 57,
        bottomLeftCorner = 57,
        bottomRightCorner = 57)

    moviesDetailsItemBinding.productRatingStarsView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    moviesDetailsItemBinding.productRatingStarsView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    moviesDetailsItemBinding.productContentRatingView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    moviesDetailsItemBinding.productContentRatingView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    moviesDetailsItemBinding.movieGenreFirst.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    moviesDetailsItemBinding.movieGenreFirst.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    moviesDetailsItemBinding.movieGenreSecond.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    moviesDetailsItemBinding.movieGenreSecond.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    moviesDetailsItemBinding.movieGenreThird.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    moviesDetailsItemBinding.movieGenreThird.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    return optionsBackground
}