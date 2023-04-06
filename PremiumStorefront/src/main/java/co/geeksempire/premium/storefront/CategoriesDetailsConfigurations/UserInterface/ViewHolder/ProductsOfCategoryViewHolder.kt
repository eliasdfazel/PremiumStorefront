/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 10:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.ViewHolder

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.databinding.ProductsOfCategoryItemBinding
import com.google.android.material.button.MaterialButton
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class ProductsOfCategoryViewHolder (rootItemView: ProductsOfCategoryItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
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