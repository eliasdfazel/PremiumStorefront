/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/13/21, 10:17 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface.UniqueSection.ViewHolder

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.databinding.GenreUniqueMoviesItemBinding

class UniqueMoviesViewHolder (genreUniqueMoviesItemBinding: GenreUniqueMoviesItemBinding) : RecyclerView.ViewHolder(genreUniqueMoviesItemBinding.root) {
    val rootViewItem = genreUniqueMoviesItemBinding.rootViewItem

    val moviesPosterImageView: AppCompatImageView = genreUniqueMoviesItemBinding.moviesPosterImageView
}