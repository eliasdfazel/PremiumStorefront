/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 5:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.FilterAdapter

import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterViewHolder.FilterOptionsViewHolder
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilterAllMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.movies.databinding.MoviesFilteringLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.firestore.DocumentSnapshot

class FilterOptionsAdapter (private val context: AppCompatActivity,
                            private val filterAllMovies: FilterAllMovies,
                            private val storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                            private val filterOptionsType: String = FilteringOptions.FilterByDirector,
                            private val moviesFilteringLayoutBinding: MoviesFilteringLayoutBinding) : RecyclerView.Adapter<FilterOptionsViewHolder>() {

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
                FilteringOptions.FilterByDirector -> {

                    filterAllMovies.filterAlMoviesByInput(storefrontAllUnfilteredContents,
                        FilteringOptions.FilterByDirector,
                        filterOptionsData[position].filterOptionLabel)
                        .invokeOnCompletion {

                            Handler(Looper.getMainLooper()).postDelayed({

                                moviesFilteringLayoutBinding.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                                moviesFilteringLayoutBinding.root.visibility = View.GONE

                            }, 531)

                        }

                }
                FilteringOptions.FilterByStudio -> {

                    filterAllMovies.filterAlMoviesByInput(storefrontAllUnfilteredContents,
                        FilteringOptions.FilterByStudio,
                        filterOptionsData[position].filterOptionLabel)
                        .invokeOnCompletion {

                            Handler(Looper.getMainLooper()).postDelayed({

                                moviesFilteringLayoutBinding.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                                moviesFilteringLayoutBinding.root.visibility = View.GONE

                            }, 531)

                        }

                }
            }

        }

    }

}