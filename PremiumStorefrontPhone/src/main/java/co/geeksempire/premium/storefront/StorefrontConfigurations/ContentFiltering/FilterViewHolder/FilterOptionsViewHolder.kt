/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 4:48 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.filter_options_item_layout.view.*

class FilterOptionsViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val rootViewItem: ConstraintLayout = rootView.rootViewItem

    val filterOptionsLabel: TextView = rootView.filterOptionsLabel
    val filterOptionsIcon: ImageView = rootView.filterOptionsIcon
}