/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 12:37 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorited_product_item.view.*
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class FavoritedViewHolder (rootView: View) : RecyclerView.ViewHolder (rootView) {
    val rootViewItem: ConstraintLayout = rootView.rootViewItem

    val productIconImageView: AppCompatImageView = rootView.productIconImageView
    val productNameTextView: TextView = rootView.productNameTextView
    val productSummaryTextView: TextView = rootView.productSummaryTextView

    val blurryBackgroundItem: RealtimeBlurView = rootView.blurryBackgroundItem
}