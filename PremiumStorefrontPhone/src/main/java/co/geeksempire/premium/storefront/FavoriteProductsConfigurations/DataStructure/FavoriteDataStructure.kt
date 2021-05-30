/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/30/21, 1:00 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure

object Favorite {
    const val ProductFavorited: String = "productFavorited"
}

data class FavoriteDataStructure(var productId: String, var productFavorited: Boolean)