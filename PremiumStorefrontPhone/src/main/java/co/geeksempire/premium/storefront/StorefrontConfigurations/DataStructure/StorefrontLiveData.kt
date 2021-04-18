/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 2:50 PM
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

class StorefrontLiveData : ViewModel() {

    val featuredContentItemData: MutableLiveData<ArrayList<StorefrontFeaturedContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontFeaturedContentsData>>()
    }

    fun processFeaturedContent(featuredContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val storefrontFeaturedContents = ArrayList<StorefrontFeaturedContentsData>()

        for (i in 0 until featuredContentJsonArray.length()) {

//            storefrontFeaturedContents.add(StorefrontFeaturedContentsData())

        }

        featuredContentItemData.postValue(storefrontFeaturedContents)

    }

}