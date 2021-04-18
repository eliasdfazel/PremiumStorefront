/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 2:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.retrieveFeaturedContent
import co.geeksempire.premium.storefront.Utils.NetworkConnection.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnection.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.NetworkConnection.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.databinding.StorefrontLayoutBinding

class Storefront : AppCompatActivity(), NetworkConnectionListenerInterface {

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val storefrontLiveData: StorefrontLiveData by lazy {
        ViewModelProvider(this@Storefront).get(StorefrontLiveData::class.java)
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@Storefront, storefrontLayoutBinding.rootView, networkCheckpoint)
    }

    lateinit var storefrontLayoutBinding: StorefrontLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storefrontLayoutBinding = StorefrontLayoutBinding.inflate(layoutInflater)
        setContentView(storefrontLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@Storefront

        storefrontLayoutBinding.rootView.post {

            storefrontLiveData.featuredContentItemData.observe(this@Storefront, Observer {



            })

        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {



    }

    override fun networkAvailable() {

        retrieveFeaturedContent()

    }

    override fun networkLost() {

    }

}