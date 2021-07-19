/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/19/21, 2:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.products_of_category_item.view.*
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class UniqueRecommendationsCategoryViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val productIconImageView: AppCompatImageView = rootItemView.productIconImageView

    val productNameTextView: TextView = rootItemView.productNameTextView
    val productSummaryTextView: TextView = rootItemView.productSummaryTextView
    val productCurrentRateView: TextView = rootItemView.productCurrentRateView

    val installView: MaterialButton = rootItemView.installView

    val productRatingStarsView: ImageView = rootItemView.productRatingStarsView
    val productDividerView: ImageView = rootItemView.productDividerView

    val blurryBackground: RealtimeBlurView = rootItemView.blurryBackground
}