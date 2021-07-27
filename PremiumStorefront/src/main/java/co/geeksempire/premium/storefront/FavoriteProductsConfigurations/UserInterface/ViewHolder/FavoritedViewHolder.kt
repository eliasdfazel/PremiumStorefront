/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/14/21, 12:11 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.favorited_product_item.view.*
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class FavoritedViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootViewItem: ConstraintLayout = rootItemView.rootViewItem

    val productIconImageView: ImageView = rootItemView.productIconImageView
    val productNameTextView: TextView = rootItemView.productNameTextView
    val productSummaryTextView: TextView = rootItemView.productSummaryTextView

    val installView: MaterialButton = rootItemView.installView
    val removeView: MaterialButton = rootItemView.removeView

    val blurryBackgroundItem: RealtimeBlurView = rootItemView.blurryBackgroundItem
}