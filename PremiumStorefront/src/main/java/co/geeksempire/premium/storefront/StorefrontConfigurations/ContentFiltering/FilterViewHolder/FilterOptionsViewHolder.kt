/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/3/21, 10:04 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterViewHolder

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.databinding.FilterOptionsItemLayoutBinding

class FilterOptionsViewHolder(filterOptionsItemLayoutBinding: FilterOptionsItemLayoutBinding) : RecyclerView.ViewHolder(filterOptionsItemLayoutBinding.root) {
    val rootViewItem: ConstraintLayout = filterOptionsItemLayoutBinding.rootViewItem

    val filterOptionsLabel: TextView = filterOptionsItemLayoutBinding.filterOptionsLabel
    val filterOptionsIcon: ImageView = filterOptionsItemLayoutBinding.filterOptionsIcon
}