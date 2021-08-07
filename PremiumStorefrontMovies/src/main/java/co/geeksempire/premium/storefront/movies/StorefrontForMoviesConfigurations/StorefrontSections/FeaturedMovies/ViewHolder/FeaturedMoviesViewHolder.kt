/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/7/21, 9:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.ViewHolder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.storefront_featured_movies_item.view.*
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class FeaturedMoviesViewHolder (rootViewItem: View) : RecyclerView.ViewHolder(rootViewItem) {
    val rootViewItem: ConstraintLayout = rootViewItem.rootViewItem

    val featuredMovieBackground: AppCompatImageView = rootViewItem.featuredMovieBackground
    val moviePosterBackground: AppCompatImageView = rootViewItem.moviePosterBackground
    val movieContentBackground: AppCompatImageView = rootViewItem.movieContentBackground
    val moviePosterImageView: AppCompatImageView = rootViewItem.moviePosterImageView
    val productRatingStarsView: AppCompatImageView = rootViewItem.productRatingStarsView
    val productContentRatingView: AppCompatImageView = rootViewItem.productContentRatingView

    val movieGenreFirst: AppCompatImageView = rootViewItem.movieGenreFirst
    val movieGenreSecond: AppCompatImageView = rootViewItem.movieGenreSecond
    val movieGenreThird: AppCompatImageView = rootViewItem.movieGenreThird

    val movieNameTextView: AppCompatTextView = rootViewItem.movieNameTextView
    val movieSummaryTextView: AppCompatTextView = rootViewItem.movieSummaryTextView
    val productCurrentRateView: AppCompatTextView = rootViewItem.productCurrentRateView

    val movieContentBackgroundBlur: RealtimeBlurView = rootViewItem.movieContentBackgroundBlur
}