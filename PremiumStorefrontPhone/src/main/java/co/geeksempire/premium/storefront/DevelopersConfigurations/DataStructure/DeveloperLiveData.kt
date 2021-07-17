/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/17/21, 8:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections.GeneralEndpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset

class DeveloperLiveData : ViewModel() {

    val developerProductsApplications: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val developerProductsGames: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    fun prepareDeveloperProductsApplications(csvOfProductsIds: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val severalProductsByIdsEndpoint = GeneralEndpoint().getSeveralProductsByIdEndpoint(csvOfProductsIds)

        val jsonOfProducts = URL(severalProductsByIdsEndpoint).readText(Charset.defaultCharset())

        val jsonArrayOfProducts = JSONArray(jsonOfProducts)

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until jsonArrayOfProducts.length()) {

            val applicationsContentJsonObject: JSONObject = jsonArrayOfProducts[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = applicationsContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = applicationsContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(
                ProductsContentKey.NameKey)

            for (indexCategory in 0 until productCategories.length()) {

                val allTextCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(
                    ProductsContentKey.NameKey).split(" ")[0]

                if (allTextCheckpoint != "All" && allTextCheckpoint != "Quick") {

                    productCategory = (productCategories[indexCategory] as JSONObject).getString(
                        ProductsContentKey.NameKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = applicationsContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(
                    ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = applicationsContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = applicationsContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = applicationsContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategory,
                productPrice = applicationsContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = applicationsContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            println(">>> >> > " + applicationsContentJsonObject.getString(ProductsContentKey.NameKey))

            Log.d(this@DeveloperLiveData.javaClass.simpleName, "All Products: ${applicationsContentJsonObject.getString(
                ProductsContentKey.NameKey)}")
        }

        developerProductsApplications.postValue(storefrontAllContents)

    }

    fun prepareDeveloperProductsGames(csvOfProductsIds: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val severalProductsByIdsEndpoint = GeneralEndpoint().getSeveralProductsByIdEndpoint(csvOfProductsIds)

        val jsonOfProducts = URL(severalProductsByIdsEndpoint).readText(Charset.defaultCharset())

        val jsonArrayOfProducts = JSONArray(jsonOfProducts)

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until jsonArrayOfProducts.length()) {

            val applicationsContentJsonObject: JSONObject = jsonArrayOfProducts[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = applicationsContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = applicationsContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(
                ProductsContentKey.NameKey)

            for (indexCategory in 0 until productCategories.length()) {

                val allTextCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(
                    ProductsContentKey.NameKey).split(" ")[0]

                if (allTextCheckpoint != "All" && allTextCheckpoint != "Quick") {

                    productCategory = (productCategories[indexCategory] as JSONObject).getString(
                        ProductsContentKey.NameKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = applicationsContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(
                    ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = applicationsContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = applicationsContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = applicationsContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategory,
                productPrice = applicationsContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = applicationsContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            println(">>> >> > " + applicationsContentJsonObject.getString(ProductsContentKey.NameKey))

            Log.d(this@DeveloperLiveData.javaClass.simpleName, "All Products: ${applicationsContentJsonObject.getString(
                ProductsContentKey.NameKey)}")
        }

        developerProductsApplications.postValue(storefrontAllContents)

    }

}