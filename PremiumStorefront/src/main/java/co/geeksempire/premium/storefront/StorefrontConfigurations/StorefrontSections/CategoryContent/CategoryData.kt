/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/2/21, 9:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.CategoryContent

import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontCategoriesData
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