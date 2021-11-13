/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/13/21, 5:43 AM
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
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset

class DeveloperLiveData : ViewModel() {

    val developerProductsApplications: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val developerProductsGames: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    fun prepareDeveloperProductsApplications(csvOfProductsIds: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        if (csvOfProductsIds.isNotBlank()) {

            val severalProductsByIdsEndpoint = GeneralEndpoints().getSeveralProductsByIdEndpoint(csvOfProductsIds)

            val jsonOfProducts = URL(severalProductsByIdsEndpoint).readText(Charset.defaultCharset())

            val jsonArrayOfProducts = JSONArray(jsonOfProducts)

            val storefrontAllContents = ArrayList<DocumentSnapshot>()

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

                }
                /* End - Primary Category */

                /* Start - Attributes */
                val featuredContentAttributes: JSONArray = applicationsContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

                val attributesMap = HashMap<String, String?>()

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
                    productCategoryName = productCategoryName,
                    productCategoryId = productCategoryId,
                    productPrice = applicationsContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                    productSalePrice = applicationsContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = productCover,
                    productAttributes = attributesMap
                ))

                Log.d(this@DeveloperLiveData.javaClass.simpleName, "All Products: ${applicationsContentJsonObject.getString(
                    ProductsContentKey.NameKey)}")
            }

            developerProductsApplications.postValue(storefrontAllContents)

        }

    }

    fun prepareDeveloperProductsGames(csvOfProductsIds: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        if (csvOfProductsIds.isNotBlank()) {

            val severalProductsByIdsEndpoint = GeneralEndpoints().getSeveralProductsByIdEndpoint(csvOfProductsIds)

            val jsonOfProducts = URL(severalProductsByIdsEndpoint).readText(Charset.defaultCharset())

            val jsonArrayOfProducts = JSONArray(jsonOfProducts)

            val storefrontAllContents = ArrayList<DocumentSnapshot>()

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

                }
                /* End - Primary Category */

                /* Start - Attributes */
                val featuredContentAttributes: JSONArray = applicationsContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

                val attributesMap = HashMap<String, String?>()

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
                    productCategoryName = productCategoryName,
                    productCategoryId = productCategoryId,
                    productPrice = applicationsContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                    productSalePrice = applicationsContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = productCover,
                    productAttributes = attributesMap
                ))

                Log.d(this@DeveloperLiveData.javaClass.simpleName, "All Products: ${applicationsContentJsonObject.getString(
                    ProductsContentKey.NameKey)}")
            }

            developerProductsGames.postValue(storefrontAllContents)

        }

    }

}