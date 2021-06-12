/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Endpoint

class FavoritedDatabaseDirectory {

    fun favoriteProductsCollectionEndpoint(userUniqueIdentifier: String) = "/PremiumStorefront/${userUniqueIdentifier}/Favorite"

    fun favoriteProductEndpoint(userUniqueIdentifier: String, productId: String) = "/PremiumStorefront/${userUniqueIdentifier}/Favorite/${productId}"

}