/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/11/21, 11:17 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Endpoint

class FavoritedDatabaseDirectory {

    fun favoriteProductsCollectionEndpoint(userUniqueIdentifier: String) =
        "/" +
        "PremiumStorefront" +
        "/" +
        "UsersInformation" +
        "/" +
        "${userUniqueIdentifier}" +
        "/" +
        "Favorite"

    fun favoriteProductEndpoint(userUniqueIdentifier: String, productId: String) =
        "/" +
        "PremiumStorefront" +
        "/" +
        "UsersInformation" +
        "/" +
        "${userUniqueIdentifier}" +
        "/" +
        "Favorite" +
        "/" +
        "${productId}"

}