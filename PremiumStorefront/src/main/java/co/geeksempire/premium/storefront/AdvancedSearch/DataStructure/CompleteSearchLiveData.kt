/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 5:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.DataStructure

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject

class CompleteSearchLiveData : ViewModel() {

    /*
     * Applications
     */
    val applicationsSearchResults: MutableLiveData<ArrayList<CompleteSearchContent>> by lazy {
        MutableLiveData<ArrayList<CompleteSearchContent>>()
    }

    fun processApplicationsSearchResults(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@CompleteSearchLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<CompleteSearchContent>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(
                    ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(
                    ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(CompleteSearchContent(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategoryName,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap,
                searchResultType = GeneralEndpoints.QueryType.ApplicationsQuery
            ))

            Log.d(this@CompleteSearchLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(
                ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        applicationsSearchResults.postValue(storefrontAllContents)

    }

    /*
     * Games
     */
    val gamesSearchResults: MutableLiveData<ArrayList<CompleteSearchContent>> by lazy {
        MutableLiveData<ArrayList<CompleteSearchContent>>()
    }

    fun processGamesSearchResults(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@CompleteSearchLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<CompleteSearchContent>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(
                    ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(
                    ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(CompleteSearchContent(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategoryName,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap,
                searchResultType = GeneralEndpoints.QueryType.GamesQuery
            ))

            Log.d(this@CompleteSearchLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(
                ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        gamesSearchResults.postValue(storefrontAllContents)

    }

    /*
     * Movies
     */
    val moviesSearchResults: MutableLiveData<ArrayList<CompleteSearchContent>> by lazy {
        MutableLiveData<ArrayList<CompleteSearchContent>>()
    }

    fun processMoviesSearchResults(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@CompleteSearchLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<CompleteSearchContent>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(
                    ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(
                    ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(CompleteSearchContent(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategoryName,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap,
                searchResultType = GeneralEndpoints.QueryType.MoviesQuery
            ))

            Log.d(this@CompleteSearchLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(
                ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        moviesSearchResults.postValue(storefrontAllContents)

    }

}