/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 5:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.DataStructure

import androidx.annotation.Keep

/**
 *
 * @param productIconLink : First Image Of Product Gallery from JsonArray "images"
 * @param productCoverLink : Second Image Of Product Gallery from JsonArray "images"
 *
 **/
@Keep
data class CompleteSearchContent (var productName: String, var productDescription: String, var productSummary: String,
                                   var productCategoryName: String, var productCategoryId: Int,
                                   var productIconLink: String, var productCoverLink: String?,
                                   var productPrice: String,
                                   var productSalePrice: String,
                                   var uniqueRecommendation: Boolean = false,
                                   var productAttributes: HashMap<String, String?>,
                                   var searchResultType: String)