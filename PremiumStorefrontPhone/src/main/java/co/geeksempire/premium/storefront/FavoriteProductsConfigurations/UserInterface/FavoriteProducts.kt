/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/30/21, 1:55 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.databinding.FavoriteProductsLayoutBinding

class FavoriteProducts : AppCompatActivity() {

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