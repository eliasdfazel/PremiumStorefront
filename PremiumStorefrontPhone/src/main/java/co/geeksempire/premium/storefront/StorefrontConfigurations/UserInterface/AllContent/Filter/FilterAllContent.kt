/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/4/21, 9:45 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Filter

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

class FilterAllContent (private val storefrontLiveData: StorefrontLiveData) {

    fun searchThroughAllContent(storefrontAllContents: ArrayList<StorefrontContentsData>,
                                searchQuery: String) {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Search Query: ${searchQuery}")



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

    fun sortAllContentByInput(storefrontAllContents: ArrayList<StorefrontContentsData>,
                              sortInputParameter: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Sorting Input Data: ${sortInputParameter}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = storefrontAllContents.sortedByDescending {

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

            storefrontAllContents.clear()
            storefrontAllContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(storefrontAllContents)

        }

    }

}