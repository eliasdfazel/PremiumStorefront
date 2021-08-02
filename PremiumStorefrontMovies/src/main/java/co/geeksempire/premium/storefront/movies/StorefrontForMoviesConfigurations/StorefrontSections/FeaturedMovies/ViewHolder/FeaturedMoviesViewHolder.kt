/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 9:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.FeaturedMovies.ViewHolder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.storefront_featured_content_item.view.*

class FeaturedMoviesViewHolder (rootViewItem: View) : RecyclerView.ViewHolder(rootViewItem) {
    val rootViewItem: ConstraintLayout = rootViewItem.rootViewItem

    val featuredMovieBackground: AppCompatImageView = rootViewItem.featuredMovieBackground
    val moviePosterImageView: AppCompatImageView = rootViewItem.moviePosterImageView
}