/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/4/21 3:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Filter

import android.util.Log
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

class FilterAllContent (private val storefrontLiveData: StorefrontLiveData) {

    fun filterAllContentByCategory(storefrontAllContents: ArrayList<StorefrontContentsData>,
                                   selectedCategory: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllContent.javaClass.simpleName, "Selected Category: ${selectedCategory}")

        val storefrontAllContentsFilter = storefrontAllContents.filter {

            (it.productCategory == selectedCategory)
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsFilter)

        storefrontLiveData.allFilteredContentItemData.postValue(storefrontAllContents)

    }

}