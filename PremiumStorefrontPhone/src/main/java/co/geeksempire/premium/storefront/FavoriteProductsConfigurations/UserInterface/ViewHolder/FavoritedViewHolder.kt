/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:36 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.ViewHolder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorited_product_item.view.*

class FavoritedViewHolder (rootView: View) : RecyclerView.ViewHolder (rootView) {
    val rootViewItem: ConstraintLayout = rootView.rootViewItem

    val productIconImageView: AppCompatImageView = rootView.productIconImageView
    val productNameTextView: AppCompatTextView = rootView.productNameTextView
    val productSummaryTextView: AppCompatTextView = rootView.productSummaryTextView
}