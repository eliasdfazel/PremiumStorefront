/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 5:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter

import android.util.Log
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontFeaturedContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

object SortingOptions {
    const val SortByPrice = "productPrice"
    const val SortByRating = "Rating"
}

object FilteringOptions {
    const val FilterByCountry = "FilterByCountry"
    const val FilterByAndroidCompatibilities = "FilterByAndroidCompatibilities"
}

data class FilterOptionsItem(var filterOptionLabel: String, var filterOptionIconLink: String?)

class FilterAllContent (private val storefrontLiveData: StorefrontLiveData) {

    fun searchThroughAllContent(storefrontAllContents: ArrayList<StorefrontContentsData>,
                                searchQuery: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Search Query: ${searchQuery}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = ArrayList<StorefrontContentsData>()

            storefrontAllContents.forEachIndexed { index, storefrontContentsData ->

                val inputSearchQuery = searchQuery.lowercase()

                val productName = storefrontContentsData.productName.lowercase()
                val productSummary = storefrontContentsData.productSummary.lowercase()
                val productCategory = storefrontContentsData.productCategory.lowercase()

                val countryOfDeveloper = storefrontContentsData.productAttributes[StorefrontFeaturedContentKey.AttributesDeveloperCountryKey]?:""

                if (productName.contains(inputSearchQuery)
                    || productSummary.contains(inputSearchQuery)
                    || productCategory.contains(inputSearchQuery)
                    || countryOfDeveloper.contains(inputSearchQuery)) {

                    storefrontAllContentsFilter.add(storefrontContentsData)

                }

            }

            storefrontLiveData.allFilteredContentItemData.postValue(storefrontAllContentsFilter)

        }

    }

    fun filterAllContentByCategory(storefrontAllContents: ArrayList<StorefrontContentsData>,
                                   selectedCategory: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Selected Category: ${selectedCategory}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = storefrontAllContents.filter {

                (it.productCategory == selectedCategory)
            }

            storefrontAllContents.clear()
            storefrontAllContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(storefrontAllContents)

        }

    }

    fun filterAlContentByInput(storefrontAllContents: ArrayList<StorefrontContentsData>,
                               filterType: String, filterInputParameter: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Filtering Input Data: ${filterInputParameter}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = storefrontAllContents.filter {

                when (filterType) {
                    FilteringOptions.FilterByCountry -> {

                        it.productAttributes[StorefrontFeaturedContentKey.AttributesDeveloperCountryKey] == filterInputParameter

                    }
                    FilteringOptions.FilterByAndroidCompatibilities -> {

                        it.productAttributes[StorefrontFeaturedContentKey.AttributesAndroidCompatibilitiesKey] == filterInputParameter

                    }
                    else -> true //All Unfiltered Content
                }
            }

            storefrontAllContents.clear()
            storefrontAllContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(storefrontAllContents)

        }

    }

    fun sortAllContentByInput(storefrontFilteredContents: ArrayList<StorefrontContentsData>,
                              sortInputParameter: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Sorting Input Data | ${storefrontFilteredContents.size} Products: ${sortInputParameter}")

        if (storefrontFilteredContents.isNotEmpty()) {

            val sortedStorefrontFilteredContents = ArrayList<StorefrontContentsData>()

            val storefrontAllContentsFilter = storefrontFilteredContents.sortedByDescending {

                when (sortInputParameter) {
                    SortingOptions.SortByRating -> {

                        it.productAttributes[StorefrontFeaturedContentKey.AttributesRatingKey]

                    }
                    SortingOptions.SortByPrice -> {

                        it.productPrice

                    }
                    else -> it.productName
                }

            }

            sortedStorefrontFilteredContents.clear()
            sortedStorefrontFilteredContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(sortedStorefrontFilteredContents)

        }

    }

}