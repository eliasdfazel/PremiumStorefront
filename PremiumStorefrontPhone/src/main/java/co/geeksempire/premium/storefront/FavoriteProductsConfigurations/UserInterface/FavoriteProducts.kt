/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure.FavoriteProductsLiveData
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Endpoint.FavoritedDatabaseDirectory
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.databinding.FavoriteProductsLayoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FavoriteProducts : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@FavoriteProducts)
    }

    val favoritedDatabaseDirectory = FavoritedDatabaseDirectory()

    val favoriteProductsLiveData: FavoriteProductsLiveData by lazy {
        ViewModelProvider(this@FavoriteProducts).get(FavoriteProductsLiveData::class.java)
    }

    val firebaseUser = Firebase.auth.currentUser

    lateinit var favoriteProductsLayoutBinding: FavoriteProductsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteProductsLayoutBinding = FavoriteProductsLayoutBinding.inflate(layoutInflater)
        setContentView(favoriteProductsLayoutBinding.root)

        if (firebaseUser != null) {

            favoriteProductsLiveData.favoritedContentItemData.observe(this@FavoriteProducts, {



            })

            (application as PremiumStorefrontApplication).firestoreDatabase
                .collection(favoritedDatabaseDirectory.favoriteProductsCollectionEndpoint(firebaseUser!!.uid))
                .get().addOnSuccessListener { querySnapshot ->

                    favoriteProductsLiveData.prepareFavoritedProductsData(querySnapshot)

                }.addOnFailureListener {

                    this@FavoriteProducts.finish()

                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_left)

                }

        } else {

            this@FavoriteProducts.finish()

            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_left)

        }

    }

    override fun onBackPressed() {

        this@FavoriteProducts.finish()

        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_left)

    }

}