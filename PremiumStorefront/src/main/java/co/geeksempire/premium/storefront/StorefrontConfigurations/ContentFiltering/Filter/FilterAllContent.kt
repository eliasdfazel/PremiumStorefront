/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/12/21, 7:04 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter

import android.util.Log
import androidx.annotation.Keep
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataStructure
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import com.google.firebase.firestore.DocumentSnapshot
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

    fun searchThroughAllContent(storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                                searchQuery: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Search Query: ${searchQuery}")

        if (storefrontAllUnfilteredContents.isNotEmpty()) {

            val storefrontAllContentsFilter = ArrayList<DocumentSnapshot>()

            storefrontAllUnfilteredContents.forEachIndexed { index, storefrontContentsData ->

                val inputSearchQuery = searchQuery.lowercase()

                storefrontContentsData.data?.let {

                    val productDataStructure = ProductDataStructure(it)

                    val productName = productDataStructure.productName().lowercase()
                    val productSummary = productDataStructure.productSummary().lowercase()
                    val productCategory = productDataStructure.productCategoryName().lowercase()

                    val countryOfDeveloper = (productDataStructure.softwareDeveloperCountry()?:"").lowercase()
                    val cityOfDeveloper = (productDataStructure.softwareDeveloperCity()?:"").lowercase()

                    if (productName.contains(inputSearchQuery)
                        || productSummary.contains(inputSearchQuery)
                        || productCategory.contains(inputSearchQuery)
                        || countryOfDeveloper.contains(inputSearchQuery)
                        || cityOfDeveloper.contains(inputSearchQuery)) {

                        storefrontAllContentsFilter.add(storefrontContentsData)

                    }

                }

            }

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(storefrontAllContentsFilter, false))

        }

    }

    fun filterAllContentsByCategory(storefrontAllContents: ArrayList<DocumentSnapshot>,
                                    selectedCategory: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Selected Category: ${selectedCategory}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = storefrontAllContents.filter {

                val productDataStructure = ProductDataStructure(it.data!!)

                (productDataStructure.productCategoryName().split(" ").first().lowercase() == selectedCategory.lowercase())
            }

            storefrontAllContents.clear()
            storefrontAllContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(storefrontAllContents, false))

        }

    }

    fun filterAlContentByInput(storefrontAllContents: ArrayList<DocumentSnapshot>,
                               filterType: String, filterInputParameter: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Filtering Input Data: ${filterInputParameter}")

        if (storefrontAllContents.isNotEmpty()) {

            val storefrontAllContentsFilter = storefrontAllContents.filter {

                val productDataStructure = ProductDataStructure(it.data!!)

                when (filterType) {
                    FilteringOptions.FilterByCountry -> {

                        productDataStructure.softwareDeveloperCountry() == filterInputParameter

                    }
                    FilteringOptions.FilterByAndroidCompatibilities -> {

                        productDataStructure.androidCompatibility() == filterInputParameter

                    }
                    else -> true //All Unfiltered Content
                }
            }

            storefrontAllContents.clear()
            storefrontAllContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(storefrontAllContents, false))

        }

    }

    fun sortAllContentByInput(storefrontFilteredContents: ArrayList<DocumentSnapshot>,
                              sortInputParameter: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Sorting Input Data | ${storefrontFilteredContents.size} Products: ${sortInputParameter}")

        if (storefrontFilteredContents.isNotEmpty()) {

            val sortedStorefrontFilteredContents = ArrayList<DocumentSnapshot>()

            val storefrontAllContentsFilter = storefrontFilteredContents.sortedByDescending {

                val productDataStructure = ProductDataStructure(it.data!!)

                when (sortInputParameter) {
                    SortingOptions.SortByRating -> {

                        productDataStructure.productRating()

                    }
                    SortingOptions.SortByPrice -> {

                        productDataStructure.productPrice()

                    }
                    else -> productDataStructure.productName()
                }

            }

            sortedStorefrontFilteredContents.clear()
            sortedStorefrontFilteredContents.addAll(storefrontAllContentsFilter)

            storefrontLiveData.allFilteredContentItemData.postValue(Pair(sortedStorefrontFilteredContents, false))

        }

    }

}