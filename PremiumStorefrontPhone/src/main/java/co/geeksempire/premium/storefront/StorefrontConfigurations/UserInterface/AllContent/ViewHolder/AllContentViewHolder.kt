/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/17/21, 1:01 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.storefront_all_content_item.view.*

class AllContentViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val productIconImageView: AppCompatImageView = rootItemView.productIconImageView

    val productNameTextView: TextView = rootItemView.productNameTextView
    val productSummaryTextView: TextView = rootItemView.productSummaryTextView
    val productCurrentRateView: TextView = rootItemView.productCurrentRateView

    val installView: MaterialButton = rootItemView.installView

    val productDividerView: ImageView = rootItemView.productDividerView
}