/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 7:47 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.ViewHolder

import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.databinding.CompleteSearchLayoutItemBinding
import net.geeksempire.blurry.effect.view.RealtimeBlurView


class CompleteSearchViewHolder (rootItemView: CompleteSearchLayoutItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootViewItem: ConstraintLayout = rootItemView.rootViewItem

    val blurryBackground: RealtimeBlurView = rootItemView.blurryBackground

    val productTitle: AppCompatTextView = rootItemView.productTitle

}