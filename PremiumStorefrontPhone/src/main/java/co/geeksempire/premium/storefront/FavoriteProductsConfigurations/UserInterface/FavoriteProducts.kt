/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 7:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.databinding.FavoriteProductsLayoutBinding

class FavoriteProducts : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@FavoriteProducts)
    }

    lateinit var favoriteProductsLayoutBinding: FavoriteProductsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteProductsLayoutBinding = FavoriteProductsLayoutBinding.inflate(layoutInflater)
        setContentView(favoriteProductsLayoutBinding.root)


    }

    override fun onBackPressed() {

        this@FavoriteProducts.finish()

        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_left)

    }

}