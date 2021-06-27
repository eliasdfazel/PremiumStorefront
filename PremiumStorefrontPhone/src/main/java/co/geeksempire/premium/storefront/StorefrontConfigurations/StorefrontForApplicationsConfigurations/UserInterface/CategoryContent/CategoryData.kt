/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/27/21, 11:02 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.CategoryContent

import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.StorefrontCategoriesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class CategoryData {

    private val allCategoriesData: HashMap<String, StorefrontCategoriesData>  = HashMap<String, StorefrontCategoriesData>()

    fun prepareAllCategoriesData(inputData: ArrayList<StorefrontCategoriesData>) = CoroutineScope(Dispatchers.IO).async {

        inputData.forEach {

            allCategoriesData[it.categoryName] = it

        }

    }

    fun getCategoryIconByName(categoryName: String) : String? {

        return if (allCategoriesData.isNotEmpty()) {
            allCategoriesData[categoryName]?.categoryIconLink
        } else {
            null
        }
    }

    fun clearData() {

        allCategoriesData.clear()

    }

}