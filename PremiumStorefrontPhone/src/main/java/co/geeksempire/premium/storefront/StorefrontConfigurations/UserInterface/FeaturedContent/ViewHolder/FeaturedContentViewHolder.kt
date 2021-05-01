/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/1/21 4:26 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.storefront_featured_content_item.view.*
import net.geeksempire.blurry.effect.view.RealtimeBlurView
import net.geekstools.imageview.customshapes.ShapesImage

class FeaturedContentViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val backgroundCoverImageView: ShapesImage = rootItemView.backgroundCoverImageView
    val productIconImageView: ShapesImage = rootItemView.productIconImageView
    val productNameTextView: TextView = rootItemView.productNameTextView
    val productCurrentRateView: TextView = rootItemView.productCurrentRateView
    val installView: AppCompatImageView = rootItemView.installView

    val productIconBlur: RealtimeBlurView = rootItemView.productIconBlur
    val productNameBlur: RealtimeBlurView = rootItemView.productNameBlur
    val productRateBlur: RealtimeBlurView = rootItemView.productRateBlur
}