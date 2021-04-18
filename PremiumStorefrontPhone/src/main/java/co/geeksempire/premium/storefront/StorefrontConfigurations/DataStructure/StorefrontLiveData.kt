/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 3:28 PM
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

            /* Start - Attributes */
            val featuredContentImages: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.ImagesKey] as JSONArray

            val applicationIcon = featuredContentImages[0].toString()
            val applicationCover = featuredContentImages[1].toString()

            /* Start - Attributes */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[StorefrontFeaturedContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(StorefrontFeaturedContentKey.NameKey)] = attributesJsonObject.getJSONArray(StorefrontFeaturedContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */



        }

        featuredContentItemData.postValue(storefrontFeaturedContents)

    }

}