/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/15/21, 12:01 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure

import androidx.annotation.Keep

object Favorite {
    const val ProductId = "productId"
    const val ProductName = "productName"
    const val ProductDescription = "productDescription"
    const val ProductIcon = "productIcon"

    const val ProductFavorited: String = "productFavorited"

    const val ProductType: String = "productType"
}

@Keep
data class FavoriteDataStructure(var productId: String,
                                 var productName: String,
                                 var productDescription: String,
                                 var productIcon: String,
                                 var productFavorited: Boolean,
                                 var productType: String)