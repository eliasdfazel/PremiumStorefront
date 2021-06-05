/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 7:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterOptionsItem
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilteringOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.FilterAdapter.FilterOptionsAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.math.absoluteValue

fun Storefront.filteringSetup() {

    if (storefrontLayoutBinding.sortingInclude.root.isShown) {
        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE
    }

    storefrontLayoutBinding.filteringInclude.root.visibility = if (storefrontLayoutBinding.filteringInclude.root.isShown) {
        View.GONE
    } else {
        View.VISIBLE
    }

    val viewTranslateY = (storefrontLayoutBinding.filteringInclude.filterCountryView.y - storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y).absoluteValue

    storefrontLayoutBinding.filteringInclude.root.post {

        filterByCountriesDataProcess()

    }

    storefrontLayoutBinding.filteringInclude.filterCountryView.setOnClickListener {

        filterByCountriesDataProcess()

        storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
            .translationY(-viewTranslateY)
            .apply {
                interpolator = OvershootInterpolator()
            }.start()

    }

    storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.setOnClickListener {

        filterByCompatibilitiesDataProcess()

        storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
            .translationY((storefrontLayoutBinding.filteringInclude.filterCountryView.y - storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y).absoluteValue)
            .apply {
                interpolator = OvershootInterpolator()
            }.start()

    }

}

fun Storefront.filterByCountriesDataProcess() {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        val filterOptionsAdapter = FilterOptionsAdapter(this@filterByCountriesDataProcess, FilteringOptions.FilterByCountry)

        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

        CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

            var lastLabel = "-666"

            filterOptionsAdapter.filterOptionsData.clear()

            storefrontAllUnfilteredContents.sortedBy {

                it.productAttributes[StorefrontFeaturedContentKey.AttributesDeveloperCountryKey]

            }.asFlow()
                .map {

                    it.productAttributes[StorefrontFeaturedContentKey.AttributesDeveloperCountryKey]
                }
                .flowOn(Dispatchers.IO)
                .onCompletion {

                }
                .collect { countryName ->


                    countryName?.let {

                        if (countryName != lastLabel) {

                            filterOptionsAdapter.filterOptionsData.add(FilterOptionsItem(it, null))

                        }

                        lastLabel = countryName

                    }

                }

            withContext(SupervisorJob() + Dispatchers.Main) {

                filterOptionsAdapter.notifyDataSetChanged()

            }

        }

    }

}

fun Storefront.filterByCompatibilitiesDataProcess() {

    if (storefrontAllUnfilteredContents.isNotEmpty()) {

        val filterOptionsAdapter = FilterOptionsAdapter(this@filterByCompatibilitiesDataProcess, FilteringOptions.FilterByAndroidCompatibilities)

        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
        storefrontLayoutBinding.filteringInclude.filteringOptionsRecyclerView.adapter = filterOptionsAdapter

        CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

            var lastLabel = "-666"

            filterOptionsAdapter.filterOptionsData.clear()

            storefrontAllUnfilteredContents.sortedBy {

                it.productAttributes[StorefrontFeaturedContentKey.AttributesAndroidCompatibilitiesKey]

            }.asFlow()
                .map {

                    it.productAttributes[StorefrontFeaturedContentKey.AttributesAndroidCompatibilitiesKey]
                }
                .flowOn(Dispatchers.IO)
                .onCompletion {

                }
                .collect { countryName ->


                    countryName?.let {

                        if (countryName != lastLabel) {

                            filterOptionsAdapter.filterOptionsData.add(FilterOptionsItem(it, null))

                        }

                        lastLabel = countryName

                    }

                }

            withContext(SupervisorJob() + Dispatchers.Main) {

                filterOptionsAdapter.notifyDataSetChanged()

            }

        }

    }

}