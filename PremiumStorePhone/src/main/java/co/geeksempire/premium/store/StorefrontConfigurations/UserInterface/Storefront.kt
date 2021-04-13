/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/13/21 8:40 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.store.StorefrontConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.store.databinding.StorefrontLayoutBinding

class Storefront : AppCompatActivity() {

    lateinit var storefrontLayoutBinding: StorefrontLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storefrontLayoutBinding = StorefrontLayoutBinding.inflate(layoutInflater)
        setContentView(storefrontLayoutBinding.root)



    }

}