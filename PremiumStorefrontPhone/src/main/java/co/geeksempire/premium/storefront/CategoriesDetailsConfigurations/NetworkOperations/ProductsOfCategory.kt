/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/20/21, 8:39 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.NetworkOperations

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.ProductsOfCategoryAdapter
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.UniqueRecommendationsCategoryAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import kotlinx.coroutines.*
import net.geeksempire.loadingspin.SpinKitView
import org.json.JSONArray
import org.json.JSONObject

class ProductsOfCategory(val context: Context,
                         val productsOfCategoryAdapter: ProductsOfCategoryAdapter,
                         val uniqueRecommendationsCategoryAdapter: UniqueRecommendationsCategoryAdapter,
                         val loadingView: SpinKitView) {

    private val generalEndpoint = GeneralEndpoint()

    private var numberOfPageToRetrieve: Int = 1

    var allLoadingFinished: Boolean = false

    fun retrieveProductsOfCategory(categoryId: Int) {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                processAllContentOfCategories(
                    allContentJsonArray = rawDataJsonArray,
                    offsetIndex = productsOfCategoryAdapter.itemCount,
                    productsOfCategoryAdapter = productsOfCategoryAdapter,
                    uniqueRecommendationsCategoryAdapter = uniqueRecommendationsCategoryAdapter,
                    loadingView = loadingView
                )

                if (rawDataJsonArray.length() == generalEndpoint.defaultProductsPerPage) {

                    numberOfPageToRetrieve++

                    Handler(Looper.getMainLooper()).postDelayed({

                        retrieveProductsOfCategory(categoryId)

                    }, 1579)

                } else {

                    allLoadingFinished = true

                }

            }

        }).getMethod(generalEndpoint.getProductsSpecificCategoriesEndpoint(productCategoryId = categoryId, numberOfPage = numberOfPageToRetrieve))

    }

    fun processAllContentOfCategories(
        allContentJsonArray: JSONArray, offsetIndex: Int = 0,
        productsOfCategoryAdapter: ProductsOfCategoryAdapter, uniqueRecommendationsCategoryAdapter: UniqueRecommendationsCategoryAdapter,
        loadingView: SpinKitView) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@ProductsOfCategory.javaClass.simpleName, "Process All Content Of Categories")

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

            /* Start - Attributes */
            val contentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until contentAttributes.length()) {

                val attributesJsonObject: JSONObject = contentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            val adapterIndex = offsetIndex + indexContent

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

                 if (textCheckpoint.contains("Unique")) {

                     uniqueRecommendationsCategoryAdapter.storefrontContents.add(
                         StorefrontContentsData(
                             productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                             productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                             productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                             productCategoryName = productCategoryName,
                             productCategoryId = productCategoryId,
                             productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                             productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                             productIconLink = productIcon,
                             productCoverLink = productCover,
                             productAttributes = attributesMap
                         )
                     )

                 }

            }
            /* End - Primary Category */

            productsOfCategoryAdapter.storefrontContents.add(
                adapterIndex,
                StorefrontContentsData(
                    productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                    productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                    productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                    productCategoryName = productCategoryName,
                    productCategoryId = productCategoryId,
                    productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                    productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = productCover,
                    productAttributes = attributesMap
                )
            )

            withContext(Dispatchers.Main) {

                productsOfCategoryAdapter.notifyItemInserted(adapterIndex)

                uniqueRecommendationsCategoryAdapter.notifyItemInserted(uniqueRecommendationsCategoryAdapter.itemCount)

            }

            Log.d(
                this@ProductsOfCategory.javaClass.simpleName,
                "All Products Of Category: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}"
            )

            delay(197)

        }

        withContext(Dispatchers.Main) {

            loadingView.visibility = View.GONE

        }

    }

}