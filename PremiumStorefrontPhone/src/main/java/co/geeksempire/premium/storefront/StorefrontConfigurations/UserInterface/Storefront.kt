/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/28/21 1:17 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Action.Operation.ActionCenterOperations
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Adapter.FeaturedContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Extensions.setupUserInterface
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.databinding.StorefrontLayoutBinding

class Storefront : AppCompatActivity(), NetworkConnectionListenerInterface {

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val storefrontLiveData: StorefrontLiveData by lazy {
        ViewModelProvider(this@Storefront).get(StorefrontLiveData::class.java)
    }

    val actionCenterOperations: ActionCenterOperations by lazy {
        ActionCenterOperations(this@Storefront)
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

            actionCenterOperations.setupForStorefront()

            setupUserInterface()

            val featuredContentAdapter = FeaturedContentAdapter(this@Storefront)

            storefrontLayoutBinding.featuredContentRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)

            storefrontLayoutBinding.featuredContentRecyclerView.adapter = featuredContentAdapter

            storefrontLiveData.featuredContentItemData.observe(this@Storefront, Observer {

                if (it.isNotEmpty()) {

                    featuredContentAdapter.storefrontFeaturedContents.clear()
                    featuredContentAdapter.storefrontFeaturedContents.addAll(it)

                    featuredContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.featuredContentRecyclerView.scrollToPosition(0)

                } else {



                }

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

        startActivity(Intent(Intent.ACTION_MAIN).apply {
            this.addCategory(Intent.CATEGORY_HOME)
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())

    }

    override fun networkAvailable() {

//        retrieveFeaturedContent()

    }

    override fun networkLost() {

    }

}