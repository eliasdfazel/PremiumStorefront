/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/30/21, 11:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.databinding.EntryConfigurationsLayoutBinding

class EntryConfigurationsMovies : AppCompatActivity() {

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }
    lateinit var entryConfigurationsLayoutBinding: EntryConfigurationsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryConfigurationsLayoutBinding = EntryConfigurationsLayoutBinding.inflate(layoutInflater)
        setContentView(entryConfigurationsLayoutBinding.root)

        if (networkCheckpoint.networkConnection()) {



        }

    }

}