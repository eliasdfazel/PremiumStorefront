/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/15/21, 12:30 PM
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
import co.geeksempire.premium.storefront.R
import com.google.android.material.button.MaterialButton
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class FavoritedViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootViewItem: ConstraintLayout = rootItemView.findViewById(R.id.rootViewItem)

    val productIconImageView: ImageView = rootItemView.findViewById(R.id.productIconImageView)
    val productNameTextView: TextView = rootItemView.findViewById(R.id.productNameTextView)
    val productSummaryTextView: TextView = rootItemView.findViewById(R.id.productSummaryTextView)

    val installView: MaterialButton = rootItemView.findViewById(R.id.installView)
    val removeView: MaterialButton = rootItemView.findViewById(R.id.removeView)

    val blurryBackgroundItem: RealtimeBlurView = rootItemView.findViewById(R.id.blurryBackgroundItem)
}