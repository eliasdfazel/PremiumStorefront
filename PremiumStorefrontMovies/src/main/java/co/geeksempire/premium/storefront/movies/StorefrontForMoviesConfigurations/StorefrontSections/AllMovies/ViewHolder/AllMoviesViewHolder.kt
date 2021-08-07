/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/7/21, 9:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.AllMovies.ViewHolder

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.databinding.StorefrontAllContentItemBinding

class AllMoviesViewHolder (rootItemView: StorefrontAllContentItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootViewItem: ConstraintLayout = rootItemView.rootViewItem

    val moviesPosterImageView: ImageView = rootItemView.moviesPosterImageView
}