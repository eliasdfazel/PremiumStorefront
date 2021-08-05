/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/5/21, 11:17 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.GenreContent.ViewHolder

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.databinding.StorefrontCategoryItemBinding

class GenresViewHolder (rootItemView: StorefrontCategoryItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val genreIconImageView: ImageView = rootItemView.categoryIconImageView
}