/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/12/21, 6:44 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterAdapter

import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterViewHolder.FilterOptionsViewHolder
import co.geeksempire.premium.storefront.databinding.FilterOptionsItemLayoutBinding
import co.geeksempire.premium.storefront.databinding.FilteringLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.firestore.DocumentSnapshot

class FilterOptionsAdapter (private val context: AppCompatActivity,
                            private val filterAllContent: FilterAllContent,
                            private val storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                            var filterOptionsType: String,
                            private val filteringInclude: FilteringLayoutBinding) : RecyclerView.Adapter<FilterOptionsViewHolder>() {

    val filterOptionsData: ArrayList<FilterOptionsItem> = ArrayList<FilterOptionsItem>()

    override fun getItemCount(): Int {

        return filterOptionsData.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FilterOptionsViewHolder {

        return FilterOptionsViewHolder(FilterOptionsItemLayoutBinding.inflate(context.layoutInflater, viewGroup, false))
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

                    filterAllContent.filterAlContentByInput(storefrontAllUnfilteredContents,
                        FilteringOptions.FilterByCountry,
                        filterOptionsData[position].filterOptionLabel)
                        .invokeOnCompletion {

                            Handler(Looper.getMainLooper()).postDelayed({

                                filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                                filteringInclude.root.visibility = View.GONE

                            }, 531)

                        }

                }
                FilteringOptions.FilterByAndroidCompatibilities -> {

                    filterAllContent.filterAlContentByInput(storefrontAllUnfilteredContents,
                        FilteringOptions.FilterByAndroidCompatibilities,
                        filterOptionsData[position].filterOptionLabel)
                        .invokeOnCompletion {

                            Handler(Looper.getMainLooper()).postDelayed({

                                filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                                filteringInclude.root.visibility = View.GONE

                            }, 531)

                        }

                }
            }

        }

    }

}