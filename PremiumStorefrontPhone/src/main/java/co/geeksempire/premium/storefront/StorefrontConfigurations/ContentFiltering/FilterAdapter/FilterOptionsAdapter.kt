/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/6/21, 5:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterAdapter

import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterViewHolder.FilterOptionsViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class FilterOptionsAdapter (val context: Storefront, val filterOptionsType: String) : RecyclerView.Adapter<FilterOptionsViewHolder>() {

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

            when (filterOptionsType) {
                FilteringOptions.FilterByCountry -> {

                    context.filterAllContent.filterAlContentByInput(context.storefrontAllUnfilteredContents,
                        FilteringOptions.FilterByCountry,
                        filterOptionsData[position].filterOptionLabel)
                        .invokeOnCompletion {

                            Handler(Looper.getMainLooper()).postDelayed({

                                context.storefrontLayoutBinding.filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                                context.storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE

                            }, 531)

                        }

                }
                FilteringOptions.FilterByAndroidCompatibilities -> {

                    context.filterAllContent.filterAlContentByInput(context.storefrontAllUnfilteredContents,
                        FilteringOptions.FilterByAndroidCompatibilities,
                        filterOptionsData[position].filterOptionLabel)
                        .invokeOnCompletion {

                            Handler(Looper.getMainLooper()).postDelayed({

                                context.storefrontLayoutBinding.filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                                context.storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE

                            }, 531)

                        }

                }
            }

        }

    }

}