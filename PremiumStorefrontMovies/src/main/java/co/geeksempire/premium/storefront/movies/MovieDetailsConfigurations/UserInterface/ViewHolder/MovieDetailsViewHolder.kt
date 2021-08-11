/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 9:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.MovieDetailsConfigurations.UserInterface.ViewHolder

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.movies.databinding.MoviesDetailsItemBinding

class MovieDetailsViewHolder (rootItemView: MoviesDetailsItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootViewItem: NestedScrollView = rootItemView.rootViewItem

    val allItemContainer: ConstraintLayout = rootItemView.allItemContainer
}