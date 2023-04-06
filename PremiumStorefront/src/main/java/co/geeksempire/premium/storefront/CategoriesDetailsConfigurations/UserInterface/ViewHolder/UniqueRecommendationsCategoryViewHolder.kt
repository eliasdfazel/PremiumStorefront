/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/19/21, 3:19 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.ViewHolder

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.databinding.UniqueSectionItemBinding

class UniqueRecommendationsCategoryViewHolder (rootItemView: UniqueSectionItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val verticalArtImageView: ImageView = rootItemView.verticalArtImageView
}