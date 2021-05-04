/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/4/21 4:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Actions.Operation.ActionCenterOperations
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.setupUserInterface
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.retrieveAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.retrieveCategories
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.retrieveFeaturedContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.retrieveNewContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Adapter.AllContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.Adapter.CategoriesAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Adapter.FeaturedContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.NewContent.Adapter.NewContentAdapter
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.databinding.StorefrontLayoutBinding

class Storefront : AppCompatActivity(), NetworkConnectionListenerInterface {

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val storefrontLiveData: StorefrontLiveData by lazy {
        ViewModelProvider(this@Storefront).get(StorefrontLiveData::class.java)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = storefrontLayoutBinding.actionCenterView, actionLeftView = storefrontLayoutBinding.leftActionView, actionMiddleView = storefrontLayoutBinding.middleActionView, actionRightView = storefrontLayoutBinding.rightActionView)
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

    val productDetailsFragment = ProductDetailsFragment()

    val storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    lateinit var storefrontLayoutBinding: StorefrontLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storefrontLayoutBinding = StorefrontLayoutBinding.inflate(layoutInflater)
        setContentView(storefrontLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@Storefront

        storefrontLayoutBinding.rootView.post {

            actionCenterOperations.setupForStorefront()

            setupUserInterface()

            val filterAllContent = FilterAllContent(storefrontLiveData)

            val featuredContentAdapter = FeaturedContentAdapter(this@Storefront)
            storefrontLayoutBinding.featuredContentRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.featuredContentRecyclerView.adapter = featuredContentAdapter

            val allContentAdapter = AllContentAdapter(this@Storefront)
            storefrontLayoutBinding.allContentRecyclerView.layoutManager = GridLayoutManager(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)
            storefrontLayoutBinding.allContentRecyclerView.adapter = allContentAdapter

            val newContentAdapter = NewContentAdapter(this@Storefront)
            storefrontLayoutBinding.newContentRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.newContentRecyclerView.adapter = newContentAdapter

            val categoriesAdapter = CategoriesAdapter(this@Storefront, filterAllContent)
            storefrontLayoutBinding.categoriesRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            storefrontLayoutBinding.categoriesRecyclerView.adapter = categoriesAdapter

            storefrontLiveData.allContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    if (storefrontAllUnfilteredContents.isEmpty()) {

                        storefrontAllUnfilteredContents.clear()
                        storefrontAllUnfilteredContents.addAll(it)
                    }

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it)

                    allContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.allContentRecyclerView.scrollToPosition(0)

                } else {



                }

            })

            storefrontLiveData.allFilteredContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it)

                    allContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.allContentRecyclerView.scrollToPosition(0)

                } else {



                }

            })

            storefrontLiveData.featuredContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    featuredContentAdapter.storefrontContents.clear()
                    featuredContentAdapter.storefrontContents.addAll(it)

                    featuredContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.featuredContentRecyclerView.scrollToPosition(0)

                } else {



                }

            })

            storefrontLiveData.newContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    newContentAdapter.storefrontContents.clear()
                    newContentAdapter.storefrontContents.addAll(it)

                    newContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.newContentRecyclerView.scrollToPosition(0)

                } else {



                }

            })

            storefrontLiveData.categoriesItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    categoriesAdapter.storefrontCategories.clear()
                    categoriesAdapter.storefrontCategories.addAll(it)

                    categoriesAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.newContentRecyclerView.scrollToPosition(0)

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

        if (productDetailsFragment.isShowing) {

            supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_to_right, 0)
                    .remove(productDetailsFragment)
                    .commit()

            storefrontLayoutBinding.contentDetailsContainer.visibility = View.GONE

            prepareActionCenterUserInterface.setupIconsForStorefront()

        } else {

            startActivity(Intent(Intent.ACTION_MAIN).apply {
                this.addCategory(Intent.CATEGORY_HOME)
                this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())

        }

    }

    override fun networkAvailable() {

        retrieveFeaturedContent()

        retrieveAllContent()

        retrieveCategories()

        retrieveNewContent()

    }

    override fun networkLost() {

    }

}