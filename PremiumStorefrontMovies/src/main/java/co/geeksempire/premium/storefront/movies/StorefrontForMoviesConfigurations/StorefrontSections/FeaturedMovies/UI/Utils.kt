/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/3/21, 10:57 AM
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
import co.geeksempire.premium.storefront.movies.UI.Drawable.applyClearEffectRectangle
import co.geeksempire.premium.storefront.movies.UI.Drawable.applyShadowEffectRectangle

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
        topLeftCorner = 0,
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
        bottomLeftCorner = 0,
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