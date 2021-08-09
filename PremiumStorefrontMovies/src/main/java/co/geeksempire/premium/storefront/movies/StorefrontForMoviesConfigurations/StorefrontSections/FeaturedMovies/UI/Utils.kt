/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/9/21, 7:37 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.UI

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.Adapter.FeaturedMoviesAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.ViewHolder.FeaturedMoviesViewHolder
import co.geeksempire.premium.storefront.movies.Utils.UI.Drawable.applyClearEffectRectangle
import co.geeksempire.premium.storefront.movies.Utils.UI.Drawable.applyShadowEffectRectangle

fun FeaturedMoviesAdapter.designFeaturedMoviesBackground(featuredMoviesViewHolder: FeaturedMoviesViewHolder, themeType: Boolean) : LayerDrawable {

    var featuredContentBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            context.getDrawable(R.drawable.featured_content_background_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            context.getDrawable(R.drawable.featured_content_background_dark) as LayerDrawable

        }
        else -> context.getDrawable(R.drawable.featured_content_background_light) as LayerDrawable
    }

    featuredContentBackground = applyClearEffectRectangle(negativeSpaceDrawable = featuredContentBackground, negativeSpaceLayerId = R.id.topLeftConnection,
        topLeftCorner = 0,
        topRightCorner = 0,
        bottomLeftCorner = 0,
        bottomRightCorner = 19)

    featuredContentBackground = applyClearEffectRectangle(negativeSpaceDrawable = featuredContentBackground, negativeSpaceLayerId = R.id.topRightConnection,
        topLeftCorner = 19,
        topRightCorner = 0,
        bottomLeftCorner = 19,
        bottomRightCorner = 0)

    featuredContentBackground = applyClearEffectRectangle(negativeSpaceDrawable = featuredContentBackground, negativeSpaceLayerId = R.id.bottomLeftConnection,
        topLeftCorner = 0,
        topRightCorner = 19,
        bottomLeftCorner = 0,
        bottomRightCorner = 0)

    featuredContentBackground = applyClearEffectRectangle(negativeSpaceDrawable = featuredContentBackground, negativeSpaceLayerId = R.id.bottomRightConnection,
        topLeftCorner = 19,
        topRightCorner = 0,
        bottomLeftCorner = 19,
        bottomRightCorner = 0)

    featuredContentBackground = applyClearEffectRectangle(negativeSpaceDrawable = featuredContentBackground, negativeSpaceLayerId = R.id.centerClearLayer,
        topLeftCorner = 39,
        topRightCorner = 39,
        bottomLeftCorner = 39,
        bottomRightCorner = 39)

    featuredMoviesViewHolder.featuredMovieBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    featuredMoviesViewHolder.featuredMovieBackground.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    return featuredContentBackground
}

fun FeaturedMoviesAdapter.designOptionsMoviesBackground(featuredMoviesViewHolder: FeaturedMoviesViewHolder, themeType: Boolean) : LayerDrawable {

    var featuredOptionsBackground = when (themeType) {
        ThemeType.ThemeLight -> {

            context.getDrawable(R.drawable.movie_rating_glowing_frame_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            context.getDrawable(R.drawable.movie_rating_glowing_frame_dark) as LayerDrawable

        }
        else -> context.getDrawable(R.drawable.movie_rating_glowing_frame_light) as LayerDrawable
    }

    featuredOptionsBackground = applyClearEffectRectangle(negativeSpaceDrawable = featuredOptionsBackground, negativeSpaceLayerId = R.id.clearLayer,
        topLeftCorner = 19,
        topRightCorner = 19,
        bottomLeftCorner = 19,
        bottomRightCorner = 19)

    featuredMoviesViewHolder.productRatingStarsView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    featuredMoviesViewHolder.productRatingStarsView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    featuredMoviesViewHolder.productContentRatingView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    featuredMoviesViewHolder.productContentRatingView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    featuredMoviesViewHolder.movieGenreFirst.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    featuredMoviesViewHolder.movieGenreFirst.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    featuredMoviesViewHolder.movieGenreSecond.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    featuredMoviesViewHolder.movieGenreSecond.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    featuredMoviesViewHolder.movieGenreThird.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })
    featuredMoviesViewHolder.movieGenreThird.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    return featuredOptionsBackground
}

fun FeaturedMoviesAdapter.designFeaturedMoviesPosterBackground(featuredMoviesViewHolder: FeaturedMoviesViewHolder, themeType: Boolean) : LayerDrawable {

    var featuredPostBackground = context.getDrawable(R.drawable.featured_poster_background) as LayerDrawable

    val shadowColor = when (themeType) {
        ThemeType.ThemeLight -> {

            context.getColor(R.color.premiumDark)

        }
        ThemeType.ThemeDark -> {

            context.getColor(R.color.premiumLight)

        }
        else -> context.getColor(R.color.premiumDark)
    }

    featuredPostBackground = applyShadowEffectRectangle(shadowDrawable = featuredPostBackground,
        shadowLayerId = R.id.shadowLayer,
        shadowColor = shadowColor,
        shadowRadius = 15f,
        topLeftCorner = 29,
        topRightCorner = 29,
        bottomRightCorner = 29,
        bottomLeftCorner = 29)

    featuredMoviesViewHolder.moviePosterBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        color = shadowColor

        setShadowLayer(13f, 0f, 0f, shadowColor)
    })

    return featuredPostBackground
}