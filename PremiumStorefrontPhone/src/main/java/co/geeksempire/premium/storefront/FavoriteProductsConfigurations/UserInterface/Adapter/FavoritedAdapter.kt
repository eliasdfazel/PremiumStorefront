/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 12:43 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.Adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.FavoriteDataStructure
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.FavoriteProducts
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.ViewHolder.FavoritedViewHolder
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class FavoritedAdapter (val context: FavoriteProducts, var themeType: Boolean = ThemeType.ThemeLight) : RecyclerView.Adapter<FavoritedViewHolder>() {

    val favoritedContentItems: ArrayList<FavoriteDataStructure> = ArrayList<FavoriteDataStructure>()

    override fun getItemCount(): Int {

        return favoritedContentItems.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoritedViewHolder {

        return FavoritedViewHolder(LayoutInflater.from(context).inflate(R.layout.favorited_product_item, viewGroup, false))
    }

    override fun onBindViewHolder(favoritedViewHolder: FavoritedViewHolder, position: Int) {

        when (themeType) {
            ThemeType.ThemeLight -> {

                favoritedViewHolder.blurryBackgroundItem.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

                favoritedViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                favoritedViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

            }
            ThemeType.ThemeDark -> {

                favoritedViewHolder.blurryBackgroundItem.setOverlayColor(context.getColor(R.color.premiumDarkTransparent))

                favoritedViewHolder.productNameTextView.setTextColor(context.getColor(R.color.light))
                favoritedViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.light))
            }
            else -> {

                favoritedViewHolder.blurryBackgroundItem.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

                favoritedViewHolder.productNameTextView.setTextColor(context.getColor(R.color.dark))
                favoritedViewHolder.productSummaryTextView.setTextColor(context.getColor(R.color.dark))

            }
        }

        favoritedViewHolder.productNameTextView.text = Html.fromHtml(favoritedContentItems[position].productName, Html.FROM_HTML_MODE_COMPACT)
        favoritedViewHolder.productSummaryTextView.text = Html.fromHtml(favoritedContentItems[position].productDescription, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(context)
            .asDrawable()
            .load(favoritedContentItems[position].productIcon)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .into(favoritedViewHolder.productIconImageView)

        favoritedViewHolder.rootViewItem.setOnClickListener {

            openPlayStoreToInstall(context,
                favoritedContentItems[position].productId,
                favoritedContentItems[position].productName,
                favoritedContentItems[position].productDescription)

        }

    }

}