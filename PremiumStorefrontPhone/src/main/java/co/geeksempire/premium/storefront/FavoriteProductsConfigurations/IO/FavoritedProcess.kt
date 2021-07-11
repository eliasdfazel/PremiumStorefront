/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/11/21, 12:01 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.Favorite
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.FavoriteDataStructure
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Endpoint.FavoritedDatabaseDirectory
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import com.google.android.gms.tasks.Task

interface FavoriteProductQueryInterface {
    fun favoriteProductsExist(isFavoriteProductsExist: Boolean) {}
    fun favoriteProduct(isProductFavorited: Boolean) {}
}

class FavoritedProcess (private val context: AppCompatActivity) {

    fun add(userUniqueIdentifier: String, userEmailAddress: String,
            productIdToFavorite: String, productName: String, productDescription: String, productIcon: String) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(FavoritedDatabaseDirectory().favoriteProductEndpoint(userUniqueIdentifier, productIdToFavorite))
            .set(FavoriteDataStructure(
                productId = productIdToFavorite,
                productName = productName,
                productDescription = productDescription,
                productIcon = productIcon,
                productFavorited = true))

    }

    fun remove(userUniqueIdentifier: String, userEmailAddress: String, productIdToFavorite: String) : Task<Void> {

        return (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(FavoritedDatabaseDirectory().favoriteProductEndpoint(userUniqueIdentifier, productIdToFavorite))
            .delete()
    }

    fun isProductFavorited(userUniqueIdentifier: String, userEmailAddress: String, productIdToFavorite: String,
                           favoriteProductQueryInterface: FavoriteProductQueryInterface) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(FavoritedDatabaseDirectory().favoriteProductEndpoint(userUniqueIdentifier, productIdToFavorite)).get()
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

    fun isFavoriteProductsExist(userUniqueIdentifier: String, userEmailAddress: String,
                                favoriteProductQueryInterface: FavoriteProductQueryInterface) {

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .collection(FavoritedDatabaseDirectory().favoriteProductsCollectionEndpoint(userUniqueIdentifier)).get()
            .addOnSuccessListener { querySnapshot ->

                favoriteProductQueryInterface.favoriteProductsExist(!querySnapshot.isEmpty)

            }.addOnFailureListener {

                favoriteProductQueryInterface.favoriteProductsExist(false)

            }

    }
}