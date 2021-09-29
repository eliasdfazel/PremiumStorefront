/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/29/21, 10:39 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter

import android.util.Log
import androidx.annotation.Keep
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
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

@Keep
data class FilterOptionsItem(var filterOptionLabel: String, var filterOptionIconLink: String?)

class FilterAllContent (private val storefrontLiveData: StorefrontLiveData) {

    fun searchThroughAllContent(storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData>,
                                searchQuery: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Search Query: ${searchQuery}")

        if (storefrontAllUnfilteredContents.isNotEmpty()) {

            val storefrontAllContentsFilter = ArrayList<StorefrontContentsData>()

            storefrontAllUnfilteredContents.forEachIndexed { index, storefrontContentsData ->

                val inputSearchQuery = searchQuery.lowercase()

                val productName = storefrontContentsData.productName.lowercase()
                val productSummary = storefrontContentsData.productSummary.lowercase()
                val productCategory = storefrontContentsData.productCategoryName.lowercase()

                val countryOfDeveloper = (storefrontContentsData.productAttributes[ProductsContentKey.AttributesDeveloperCountryKey]?:"").lowercase()
                val cityOfDeveloper = (storefrontContentsData.productAttributes[ProductsContentKey.AttributesDeveloperCityKey]?:"").lowercase()

                if (productName.contains(inputSearchQuery)
                    || productSummary.contains(inputSearchQuery)
                    || productCategory.contains(inputSearchQuery)
                    || countryOfDeveloper.contains(inputSearchQuery)
                    || cityOfDeveloper.contains(inputSearchQuery)) {

                    storefrontAllContentsFilter.add(storefrontContentsData)

                }

            }

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(storefrontAllContentsFilter, false))

        }

    }

    fun filterAllContentsByCategory(storefrontAllContents: ArrayList<StorefrontContentsData>,
                                    selectedCategory: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Selected Category: ${selectedCategory}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = storefrontAllContents.filter {

                (it.productCategoryName.split(" ").first().lowercase() == selectedCategory.lowercase())
            }

            storefrontAllContents.clear()
            storefrontAllContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(storefrontAllContents, false))

        }

    }

    fun filterAlContentByInput(storefrontAllContents: ArrayList<StorefrontContentsData>,
                               filterType: String, filterInputParameter: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Filtering Input Data: ${filterInputParameter}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = storefrontAllContents.filter {

                when (filterType) {
                    FilteringOptions.FilterByCountry -> {

                        it.productAttributes[ProductsContentKey.AttributesDeveloperCountryKey] == filterInputParameter

                    }
                    FilteringOptions.FilterByAndroidCompatibilities -> {

                        it.productAttributes[ProductsContentKey.AttributesAndroidCompatibilitiesKey] == filterInputParameter

                    }
                    else -> true //All Unfiltered Content
                }
            }

            storefrontAllContents.clear()
            storefrontAllContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(storefrontAllContents, false))

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

                        it.productAttributes[ProductsContentKey.AttributesRatingKey]

                    }
                    SortingOptions.SortByPrice -> {

                        it.productPrice

                    }
                    else -> it.productName
                }

            }

            sortedStorefrontFilteredContents.clear()
            sortedStorefrontFilteredContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(sortedStorefrontFilteredContents, false))

        }

    }

}