/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 9:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.CategoryContent.ViewHolder

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.databinding.StorefrontCategoryItemBinding

class CategoriesViewHolder (rootItemView: StorefrontCategoryItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val categoryIconImageView: ImageView = rootItemView.categoryIconImageView
}