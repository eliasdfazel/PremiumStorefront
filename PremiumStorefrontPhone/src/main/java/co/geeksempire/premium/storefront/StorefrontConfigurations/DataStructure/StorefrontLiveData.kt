/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/19/21 1:44 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject

class StorefrontLiveData : ViewModel() {

    val featuredContentItemData: MutableLiveData<ArrayList<StorefrontFeaturedContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontFeaturedContentsData>>()
    }

    fun processFeaturedContent(featuredContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val storefrontFeaturedContents = ArrayList<StorefrontFeaturedContentsData>()

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

            storefrontFeaturedContents.add(StorefrontFeaturedContentsData(
                    productName = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.NameKey),
                    productDescription = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                    productSummary = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.DescriptionKey),
                    productPrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.RegularPriceKey),
                    productSalePrice = featuredContentJsonObject.getString(StorefrontFeaturedContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = productCover,
                    productAttributes = attributesMap
            ))

        }

        featuredContentItemData.postValue(storefrontFeaturedContents)

    }

}