/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 5:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterAdapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterViewHolder.FilterOptionsViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class FilterOptionsAdapter (val context: Storefront) : RecyclerView.Adapter<FilterOptionsViewHolder>() {

    val filterOptionsData: ArrayList<FilterOptionsItem> = ArrayList<FilterOptionsItem>()

    override fun getItemCount(): Int {

        return filterOptionsData.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FilterOptionsViewHolder {

        return FilterOptionsViewHolder(LayoutInflater.from(context).inflate(R.layout.filter_options_item_layout, viewGroup, false))
    }

    override fun onBindViewHolder(filterOptionsViewHolder: FilterOptionsViewHolder, position: Int) {

        filterOptionsViewHolder.filterOptionsLabel.text = Html.fromHtml(filterOptionsData[position].filterOptionLabel, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(context)
            .asDrawable()
            .load(filterOptionsData[position].filterOptionIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CircleCrop())
            .into(filterOptionsViewHolder.filterOptionsIcon)

        filterOptionsViewHolder.rootViewItem.setOnClickListener {

            context.filterAllContent.filterAlContentByInput(context.storefrontAllUnfilteredContents, FilteringOptions.FilterByCountry, filterOptionsData[position].filterOptionLabel)
                .invokeOnCompletion {

                }

        }

    }

}