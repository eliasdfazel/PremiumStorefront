/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontActivity
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.databinding.EntryConfigurationsLayoutBinding
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontMovies
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage

class EntryConfigurationsMovies : StorefrontActivity() {

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    lateinit var entryConfigurationsLayoutBinding: EntryConfigurationsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryConfigurationsLayoutBinding = EntryConfigurationsLayoutBinding.inflate(layoutInflater)
        setContentView(entryConfigurationsLayoutBinding.root)

        if (networkCheckpoint.networkConnection()) {

            startActivity(Intent().apply {
                setClass(applicationContext, StorefrontMovies::class.java)
            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in_movie, 0).toBundle())

            this@EntryConfigurationsMovies.finish()

        }

    }

    override fun networkAvailable() {



    }

    override fun networkLost() {



    }

    override fun messageClicked(inAppMessage: InAppMessage, action: Action) {



    }

}