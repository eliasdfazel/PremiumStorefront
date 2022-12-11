/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 9:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.OldContent.ViewHolder

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.databinding.StorefrontOldContentItemBinding

class OldContentViewHolder (rootItemView: StorefrontOldContentItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val productCategoryImageView: AppCompatImageView = rootItemView.productCategoryImageView

    val productIconImageView: AppCompatImageView = rootItemView.productIconImageView
    val productNameTextView: TextView = rootItemView.productNameTextView
}