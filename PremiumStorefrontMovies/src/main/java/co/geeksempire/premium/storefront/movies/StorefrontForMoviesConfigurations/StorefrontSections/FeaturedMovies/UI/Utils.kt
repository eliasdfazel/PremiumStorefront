/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 10:49 AM
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
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.Adapter.FeaturedMoviesAdapter
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.ViewHolder.FeaturedMoviesViewHolder
import co.geeksempire.premium.storefront.movies.UI.Drawable.applyClearEffectRectangle

fun FeaturedMoviesAdapter.designFeaturedMoviesBackground(featuredMoviesViewHolder: FeaturedMoviesViewHolder) : LayerDrawable {

    var featuredContentBackground = context.getDrawable(R.drawable.featured_content_background) as LayerDrawable

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

    featuredMoviesViewHolder.featuredMovieBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    })

    return featuredContentBackground
}