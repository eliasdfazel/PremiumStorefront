/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/30/21, 1:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.Favorite
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.FavoriteDataStructure
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Endpoint.DatabaseDirectory
import co.geeksempire.premium.storefront.PremiumStorefrontApplication

interface FavoriteProductQueryInterface {
    fun favoriteProduct(isProductFavorited: Boolean)
}

class FavoritedProcess (private val context: AppCompatActivity) {

    fun start() : FavoritedProcess {


        return this@FavoritedProcess
    }

    fun add(userUniqueIdentifier: String, productIdToFavorite: String) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(DatabaseDirectory().favoriteProductsEndpoint(userUniqueIdentifier, productIdToFavorite))
            .set(FavoriteDataStructure(productId = productIdToFavorite, productFavorited = true))

    }

    fun remove(userUniqueIdentifier: String, productIdToFavorite: String) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(DatabaseDirectory().favoriteProductsEndpoint(userUniqueIdentifier, productIdToFavorite))
            .delete()

    }

    fun isProductFavorited(userUniqueIdentifier: String, productIdToFavorite: String,
                           favoriteProductQueryInterface: FavoriteProductQueryInterface) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(DatabaseDirectory().favoriteProductsEndpoint(userUniqueIdentifier, productIdToFavorite)).get()
            .addOnSuccessListener { documentSnapshot ->

                documentSnapshot.getBoolean(Favorite.ProductFavorited)?.let { it ->

                    favoriteProductQueryInterface.favoriteProduct(it)

                }

            }.addOnFailureListener {

                favoriteProductQueryInterface.favoriteProduct(false)

            }

    }

}