/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/3/21 1:18 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject

class StorefrontLiveData : ViewModel() {

    val featuredContentItemData: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val allContentItemData: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val newContentItemData: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    fun processAllContent(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(StorefrontFeaturedContentKey.ImageSourceKey)//.split("?")[0]
            /* End - Images */


            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(StorefrontFeaturedContentKey.NameKey)] = attributesJsonObject.getJSONArray(StorefrontFeaturedContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                productPrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = null,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(StorefrontFeaturedContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        allContentItemData.postValue(storefrontAllContents)

    }

    fun processFeaturedContent(featuredContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process Featured Content")

        val storefrontFeaturedContents = ArrayList<StorefrontContentsData>()

        for (indexFeaturedContent in 0 until featuredContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = featuredContentJsonArray[indexFeaturedContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[1] as JSONObject).getString(StorefrontFeaturedContentKey.ImageSourceKey)//.split("?")[0]
            val productCover = (featuredContentImages[2] as JSONObject).getString(StorefrontFeaturedContentKey.ImageSourceKey)//.split("?")[0]
            /* End - Images */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(StorefrontFeaturedContentKey.NameKey)] = attributesJsonObject.getJSONArray(StorefrontFeaturedContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontFeaturedContents.add(StorefrontContentsData(
                    productName = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.NameKey),
                    productDescription = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                    productSummary = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                    productPrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.RegularPriceKey),
                    productSalePrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = productCover,
                    productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "Featured Product: ${featuredContentJsonObject.getString(StorefrontFeaturedContentKey.NameKey)}")
        }

        val storefrontFeaturedContentsSorted = storefrontFeaturedContents.sortedBy {

            it.productAttributes[StorefrontFeaturedContentKey.AttributesRatingKey]
        }

        storefrontFeaturedContents.clear()
        storefrontFeaturedContents.addAll(storefrontFeaturedContentsSorted)

        featuredContentItemData.postValue(storefrontFeaturedContents)

    }

    fun processNewContent(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(StorefrontFeaturedContentKey.ImageSourceKey)//.split("?")[0]
            /* End - Images */


            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(StorefrontFeaturedContentKey.NameKey)] = attributesJsonObject.getJSONArray(StorefrontFeaturedContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                    productName = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.NameKey),
                    productDescription = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                    productSummary = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                    productPrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.RegularPriceKey),
                    productSalePrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = null,
                    productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(StorefrontFeaturedContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        newContentItemData.postValue(storefrontAllContents)

    }

}