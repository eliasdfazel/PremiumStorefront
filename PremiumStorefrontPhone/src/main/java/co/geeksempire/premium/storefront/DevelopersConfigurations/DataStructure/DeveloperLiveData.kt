/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/13/21, 1:27 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

class DeveloperLiveData : ViewModel() {

    val developerProducts: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    fun prepareDeveloperProducts() = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {



    }

}