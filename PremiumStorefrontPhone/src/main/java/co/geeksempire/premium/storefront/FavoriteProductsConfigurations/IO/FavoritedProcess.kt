/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:06 AM
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
    fun favoriteProductsExist(isFavoriteProductsExist: Boolean) {}
    fun favoriteProduct(isProductFavorited: Boolean) {}
}

class FavoritedProcess (private val context: AppCompatActivity) {

    fun add(userUniqueIdentifier: String,
            productIdToFavorite: String, productName: String, productDescription: String) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(DatabaseDirectory().favoriteProductEndpoint(userUniqueIdentifier, productIdToFavorite))
            .set(FavoriteDataStructure(
                productId = productIdToFavorite,
                productName = productName,
                productDescription = productDescription,
                productFavorited = true))

    }

    fun remove(userUniqueIdentifier: String, productIdToFavorite: String) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(DatabaseDirectory().favoriteProductEndpoint(userUniqueIdentifier, productIdToFavorite))
            .delete()

    }

    fun isProductFavorited(userUniqueIdentifier: String, productIdToFavorite: String,
                           favoriteProductQueryInterface: FavoriteProductQueryInterface) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(DatabaseDirectory().favoriteProductEndpoint(userUniqueIdentifier, productIdToFavorite)).get()
            .addOnSuccessListener { documentSnapshot ->

                if (documentSnapshot.exists()) {

                    documentSnapshot.getBoolean(Favorite.ProductFavorited)?.let { it ->

                        favoriteProductQueryInterface.favoriteProduct(it)

                    }

                } else {

                    favoriteProductQueryInterface.favoriteProduct(false)

                }

            }.addOnFailureListener {

                favoriteProductQueryInterface.favoriteProduct(false)

            }

    }

    fun isFavoriteProductsExist(userUniqueIdentifier: String,
                                favoriteProductQueryInterface: FavoriteProductQueryInterface) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .collection(DatabaseDirectory().favoriteProductsCollectionEndpoint(userUniqueIdentifier)).get()
            .addOnSuccessListener { querySnapshot ->

                favoriteProductQueryInterface.favoriteProductsExist(!querySnapshot.isEmpty)

            }.addOnFailureListener {

                favoriteProductQueryInterface.favoriteProductsExist(false)

            }

    }
}