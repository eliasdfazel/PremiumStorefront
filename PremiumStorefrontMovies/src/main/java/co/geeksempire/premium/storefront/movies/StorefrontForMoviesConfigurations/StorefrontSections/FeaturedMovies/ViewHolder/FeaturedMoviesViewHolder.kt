/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/3/21, 11:08 AM
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
import kotlinx.android.synthetic.main.storefront_featured_content_item.view.*
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class FeaturedMoviesViewHolder (rootViewItem: View) : RecyclerView.ViewHolder(rootViewItem) {
    val rootViewItem: ConstraintLayout = rootViewItem.rootViewItem

    val featuredMovieBackground: AppCompatImageView = rootViewItem.featuredMovieBackground
    val moviePosterBackground: AppCompatImageView = rootViewItem.moviePosterBackground
    val movieContentBackground: AppCompatImageView = rootViewItem.movieContentBackground
    val moviePosterImageView: AppCompatImageView = rootViewItem.moviePosterImageView

    val movieNameTextView: AppCompatTextView = rootViewItem.movieNameTextView
    val movieSummaryTextView: AppCompatTextView = rootViewItem.movieSummaryTextView

    val movieContentBackgroundBlur: RealtimeBlurView = rootViewItem.movieContentBackgroundBlur
}