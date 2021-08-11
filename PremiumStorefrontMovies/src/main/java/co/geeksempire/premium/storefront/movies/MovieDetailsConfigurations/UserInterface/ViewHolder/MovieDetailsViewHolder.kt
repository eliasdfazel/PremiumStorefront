/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 11:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder

import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsItemBinding
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class MovieDetailsViewHolder (rootItemView: MoviesDetailsItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootViewItem: ConstraintLayout = rootItemView.rootViewItem

    val allItemContainer: ConstraintLayout = rootItemView.allItemContainer

    val moviePostBackground: AppCompatImageView = rootItemView.moviePostBackground
    val blurryBackground: RealtimeBlurView = rootItemView.blurryBackground
}