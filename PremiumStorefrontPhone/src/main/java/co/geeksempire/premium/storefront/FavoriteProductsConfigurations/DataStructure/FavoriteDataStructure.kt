/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure

object Favorite {
    const val ProductId = "productId"
    const val ProductName = "productName"
    const val ProductDescription = "productDescription"

    const val ProductFavorited: String = "productFavorited"
}

data class FavoriteDataStructure(var productId: String,
                                 var productName: String,
                                 var productDescription: String,
                                 var productFavorited: Boolean)