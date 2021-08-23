/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/23/21, 8:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.MoviesStars.ViewHolder

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.databinding.MoviesStarsItemBinding
import net.geekstools.imageview.customshapes.ShapesImage

class MovieStarsViewHolder(moviesDetailsItemBinding: MoviesStarsItemBinding) : RecyclerView.ViewHolder(moviesDetailsItemBinding.root) {
    val rootView: ConstraintLayout = moviesDetailsItemBinding.rootViewItem

    val movieStarImageView: ShapesImage = moviesDetailsItemBinding.movieStarImageView
}