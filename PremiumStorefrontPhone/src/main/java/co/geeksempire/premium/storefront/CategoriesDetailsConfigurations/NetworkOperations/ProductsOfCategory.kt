/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 9:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.NetworkOperations

import android.util.Log
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetailsFragment
import co.geeksempire.premium.storefront.NetworkConnections.ProductSearchEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject


fun CategoryDetailsFragment.retrieveProductsOfCategory(categoryId: Long) {

    val productSearchEndpoint: ProductSearchEndpoint = ProductSearchEndpoint(generalEndpoint)

    GenericJsonRequest(requireContext(), object : JsonRequestResponses {

        override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
            super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

            processAllContentOfCategories(rawDataJsonArray)

        }

    }).getMethod(productSearchEndpoint.getProductsSpecificCategoriesEndpoint(productCategoryId = categoryId))

}

fun CategoryDetailsFragment.processAllContentOfCategories(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
    Log.d(this@processAllContentOfCategories.javaClass.simpleName, "Process All Content Of Categories")

    for (indexContent in 0 until allContentJsonArray.length()) {

        val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

        /* Start - Images */
        val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

        val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)//.split("?")[0]
        /* End - Images */

        val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)
        val productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(
            ProductsContentKey.NameKey)

        /* Start - Attributes */
        val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

        val attributesMap = HashMap<String, String>()

        for (indexAttribute in 0 until featuredContentAttributes.length()) {

            val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

            attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(
                ProductsContentKey.AttributeOptionsKey)[0].toString()

        }
        /* End - Attributes */

        productsOfCategoryAdapter.storefrontContents.add(indexContent,
            StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategory = productCategory,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = null,
                productAttributes = attributesMap)
        )

        productsOfCategoryAdapter.notifyItemInserted(indexContent)

        Log.d(this@processAllContentOfCategories.javaClass.simpleName, "All Products Of Category: ${featuredContentJsonObject.getString(
            ProductsContentKey.NameKey)}")

        delay(159)

    }

}