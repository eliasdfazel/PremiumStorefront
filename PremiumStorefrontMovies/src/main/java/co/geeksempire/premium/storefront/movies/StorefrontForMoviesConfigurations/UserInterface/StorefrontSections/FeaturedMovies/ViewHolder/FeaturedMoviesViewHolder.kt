/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 5:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.FeaturedMovies.ViewHolder

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.databinding.StorefrontFeaturedMoviesItemBinding
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class FeaturedMoviesViewHolder (rootViewItem: StorefrontFeaturedMoviesItemBinding) : RecyclerView.ViewHolder(rootViewItem.root) {
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