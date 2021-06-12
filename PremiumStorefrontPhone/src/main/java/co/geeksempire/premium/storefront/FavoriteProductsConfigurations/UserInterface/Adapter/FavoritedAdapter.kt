/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:47 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.Adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.FavoriteDataStructure
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.ViewHolder.FavoritedViewHolder
import co.geeksempire.premium.storefront.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class FavoritedAdapter (val context: Context) : RecyclerView.Adapter<FavoritedViewHolder>() {

    val favoritedContentItems: ArrayList<FavoriteDataStructure> = ArrayList<FavoriteDataStructure>()

    override fun getItemCount(): Int {

        return favoritedContentItems.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoritedViewHolder {

        return FavoritedViewHolder(LayoutInflater.from(context).inflate(R.layout.favorited_product_item, viewGroup, false))
    }

    override fun onBindViewHolder(favoritedViewHolder: FavoritedViewHolder, position: Int) {

        favoritedViewHolder.productNameTextView.text = Html.fromHtml(favoritedContentItems[position].productName, Html.FROM_HTML_MODE_COMPACT)
        favoritedViewHolder.productSummaryTextView.text = Html.fromHtml(favoritedContentItems[position].productDescription, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(context)
            .asDrawable()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .into(favoritedViewHolder.productIconImageView)

        favoritedViewHolder.rootViewItem.setOnClickListener {



        }

    }

}