/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/5/21, 11:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.StorefrontSections.GenreContent.ViewHolder

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R

class GenresViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootView: ConstraintLayout = rootItemView.findViewById(R.id.rootViewItem)

    val genreIconImageView: ImageView = rootItemView.findViewById(R.id.categoryIconImageView)
}