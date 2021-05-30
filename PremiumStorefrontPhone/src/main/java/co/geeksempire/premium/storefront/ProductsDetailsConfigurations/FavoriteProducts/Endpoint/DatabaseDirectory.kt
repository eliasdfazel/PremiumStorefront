/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/30/21, 12:13 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.FavoriteProducts.Endpoint

class DatabaseDirectory {

    fun favoriteProductsEndpoint(userUniqueIdentifier: String, productId: String) = "/PremiumStorefront/${userUniqueIdentifier}/Favorite/${productId}"

}