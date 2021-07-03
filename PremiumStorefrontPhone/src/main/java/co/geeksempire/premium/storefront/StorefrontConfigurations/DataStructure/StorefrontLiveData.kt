/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/3/21, 10:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.geeksempire.premium.storefront.Database.Json.JsonIO
import co.geeksempire.premium.storefront.Database.Write.InputProcess
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.AllContent.Adapter.AllContentAdapter
import co.geeksempire.premium.storefront.Utils.System.Installed
import co.geeksempire.premium.storefront.Utils.System.InstalledApplications
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.json.JSONArray
import org.json.JSONObject

class StorefrontLiveData : ViewModel() {

    val featuredContentItemData: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val allContentItemData: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val allContentMoreItemData: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val presentMoreItemData: MutableLiveData<StorefrontContentsData> by lazy {
        MutableLiveData<StorefrontContentsData>()
    }

    val allFilteredContentItemData: MutableLiveData<Pair<ArrayList<StorefrontContentsData>, Boolean>> by lazy {
        MutableLiveData<Pair<ArrayList<StorefrontContentsData>, Boolean>>()
    }

    val newContentItemData: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val categoriesItemData: MutableLiveData<ArrayList<StorefrontCategoriesData>> by lazy {
        MutableLiveData<ArrayList<StorefrontCategoriesData>>()
    }

    fun processAllContent(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(ProductsContentKey.NameKey)

            for (indexCategory in 0 until productCategories.length()) {

                val allTextCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (allTextCheckpoint != "All") {

                    productCategory = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategory,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        allContentItemData.postValue(storefrontAllContents)

    }

    fun processAllContentMore(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content And Add Them To Memory")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(ProductsContentKey.NameKey)

            for (indexCategory in 0 until productCategories.length()) {

                val allTextCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (allTextCheckpoint != "All") {

                    productCategory = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategory,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        allContentMoreItemData.postValue(storefrontAllContents)

    }

    fun processAllContentOffline(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val initialJsonArray = JSONArray()

        val targetIndex = if (allContentJsonArray.length() >= 19) {

            19

        } else {

            allContentJsonArray.length()

        }

        for (indexContent in 0 until targetIndex) {

            initialJsonArray.put(allContentJsonArray[indexContent])

            allContentJsonArray.remove(indexContent)

        }

        processAllContent(initialJsonArray)

        processAllContentMore(allContentJsonArray)

    }

    fun loadMoreDataIntoPresenter(allData: ArrayList<StorefrontContentsData>, currentAvailableData: ArrayList<StorefrontContentsData>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val moreProducts: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

        val startIndex = currentAvailableData.size
        val endIndex = if (allData.size < (currentAvailableData.size + 19)) {
            currentAvailableData.size + (allData.size - currentAvailableData.size)
        } else {
            currentAvailableData.size + 19
        }

        allData.subList(startIndex, endIndex).forEach {

            moreProducts.add(it)

            presentMoreItemData.postValue(it)

            delay(531)

        }

    }

    fun processFeaturedContent(featuredContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process Featured Content")

        val storefrontFeaturedContents = ArrayList<StorefrontContentsData>()

        for (indexFeaturedContent in 0 until featuredContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = featuredContentJsonArray[indexFeaturedContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(ProductsContentKey.NameKey)

            for (indexCategory in 0 until productCategories.length()) {

                val allTextCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (allTextCheckpoint != "All") {

                    productCategory = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontFeaturedContents.add(StorefrontContentsData(
                    productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                    productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                    productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                    productCategoryName = productCategory,
                    productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                    productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = productCover,
                    productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "Featured Product: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontFeaturedContentsSorted = storefrontFeaturedContents.sortedByDescending {

            it.productAttributes[ProductsContentKey.AttributesRatingKey]
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
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(ProductsContentKey.NameKey)

            for (indexCategory in 0 until productCategories.length()) {

                val allTextCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (allTextCheckpoint != "All") {

                    productCategory = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                    productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                    productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                    productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                    productCategoryName = productCategory,
                    productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                    productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                    productIconLink = productIcon,
                    productCoverLink = productCover,
                    productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        newContentItemData.postValue(storefrontAllContents)

    }

    fun processCategoriesList(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val storefrontCategoriesData = ArrayList<StorefrontCategoriesData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val categoryJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            val categoryId = categoryJsonObject.getLong(ProductsContentKey.IdKey)

            val categoryName = categoryJsonObject.getString(ProductsContentKey.NameKey)

            val categoryImageData = categoryJsonObject[ProductsContentKey.ImageKey]

            val categoryIconLink = (categoryImageData as JSONObject).getString(ProductsContentKey.ImageSourceKey)

            storefrontCategoriesData.add(StorefrontCategoriesData(
                    categoryId = categoryId,
                    categoryName = categoryName,
                    categoryIconLink = categoryIconLink
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "Category Name: ${categoryName}")
        }

        categoriesItemData.postValue(storefrontCategoriesData)

    }

    fun checkInstalledApplications(context: Context,
                                   allContentAdapter: AllContentAdapter,
                                   allApplications: ArrayList<StorefrontContentsData>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val installedApplicationsList: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

        val installedApplications = InstalledApplications(context)

        allApplications.forEachIndexed { index, storefrontContentsData ->

            if ((installedApplications.appIsInstalled(storefrontContentsData.productAttributes[ProductsContentKey.AttributesPackageNameKey]))) {

                allContentAdapter.storefrontContents[index].installViewText = "${context.getString(R.string.rateText)} & ${context.getString(R.string.shareText)}"

                allContentAdapter.storefrontContents[index] = storefrontContentsData

                installedApplicationsList.add(storefrontContentsData)

                delay(159)

                withContext(Dispatchers.Main) {

                    allContentAdapter.notifyItemChanged(index)

                }

            }

        }

        if (installedApplicationsList.isNotEmpty()) {

            val inputProcess = InputProcess(context)

            val jsonIO = JsonIO()

            val jsonData = jsonIO.writeArrayListToJson(installedApplicationsList)

            inputProcess.writeDataToFile(Installed.InstalledApplicationsFile, jsonData.toString())

        }

    }

}