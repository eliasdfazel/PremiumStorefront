/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/21/21, 1:31 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Actions.Operation.ActionCenterOperations
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetailsFragment
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
import co.geeksempire.premium.storefront.Utils.UI.Display.displayY
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.databinding.StorefrontLayoutBinding
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class Storefront : AppCompatActivity(), NetworkConnectionListenerInterface {

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val storefrontLiveData: StorefrontLiveData by lazy {
        ViewModelProvider(this@Storefront).get(StorefrontLiveData::class.java)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = storefrontLayoutBinding.actionCenterView, actionLeftView = storefrontLayoutBinding.leftActionView, actionMiddleView = storefrontLayoutBinding.middleActionView, actionRightView = storefrontLayoutBinding.rightActionView)
    }

    val balloonOptionsMenu: BalloonOptionsMenu by lazy {
        BalloonOptionsMenu(context = this@Storefront,
            rootView = storefrontLayoutBinding.rootView)
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

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    val categoryDetailsFragment: CategoryDetailsFragment by lazy {
        CategoryDetailsFragment()
    }

    val storefrontAllUntouchedContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()
    val storefrontAllUnfilteredContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    lateinit var storefrontLayoutBinding: StorefrontLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storefrontLayoutBinding = StorefrontLayoutBinding.inflate(layoutInflater)
        setContentView(storefrontLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@Storefront

        storefrontLayoutBinding.root.post {

            storefrontLayoutBinding.loadingView.visibility = View.VISIBLE

            setupUserInterface()

            actionCenterOperations.setupForStorefront()

            val filterAllContent = FilterAllContent(storefrontLiveData)

            val featuredContentAdapter = FeaturedContentAdapter(this@Storefront)
            storefrontLayoutBinding.featuredContentRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.featuredContentRecyclerView.adapter = featuredContentAdapter

            val allContentAdapter = AllContentAdapter(this@Storefront)
            storefrontLayoutBinding.allContentRecyclerView.layoutManager = RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)
            storefrontLayoutBinding.allContentRecyclerView.adapter = allContentAdapter

            val newContentAdapter = NewContentAdapter(this@Storefront)
            storefrontLayoutBinding.newContentRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL, false)
            storefrontLayoutBinding.newContentRecyclerView.adapter = newContentAdapter

            val categoriesAdapter = CategoriesAdapter(this@Storefront, filterAllContent)
            storefrontLayoutBinding.categoriesRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.VERTICAL, false)
            storefrontLayoutBinding.categoriesRecyclerView.adapter = categoriesAdapter

            storefrontLiveData.allContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    storefrontLayoutBinding.loadingView.visibility = View.GONE

                    val numberOfItemsToLoad = displayY(applicationContext) / (dpToInteger(applicationContext, 279)) * 3
                    Log.d(this@Storefront.javaClass.simpleName, "Number Of Items To Load | All Content: ${numberOfItemsToLoad}")

                    val dataToSetup = it.subList(0, numberOfItemsToLoad)

                    storefrontAllUntouchedContents.clear()
                    storefrontAllUntouchedContents.addAll(it)

                    storefrontAllUnfilteredContents.clear()
                    storefrontAllUnfilteredContents.addAll(it)

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it)

                    allContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.allContentRecyclerView.visibility = View.VISIBLE

                    retrieveCategories()

                } else {



                }

            })

            storefrontLiveData.allFilteredContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    allContentAdapter.storefrontContents.clear()
                    allContentAdapter.storefrontContents.addAll(it)

                    allContentAdapter.notifyDataSetChanged()

                    storefrontAllUnfilteredContents.clear()
                    storefrontAllUnfilteredContents.addAll(storefrontAllUntouchedContents)

                } else {



                }

            })

            storefrontLiveData.featuredContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    val numberOfItemsToLoad = displayY(applicationContext) / (dpToInteger(applicationContext, 307))
                    Log.d(this@Storefront.javaClass.simpleName, "Number Of Items To Load | Featured Content: ${numberOfItemsToLoad}")

                    val dataToSetup = it.subList(0, numberOfItemsToLoad)

                    featuredContentAdapter.storefrontContents.clear()
                    featuredContentAdapter.storefrontContents.addAll(it)

                    featuredContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.featuredContentRecyclerView.visibility = View.VISIBLE

                } else {



                }

            })

            storefrontLiveData.newContentItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    newContentAdapter.storefrontContents.clear()
                    newContentAdapter.storefrontContents.addAll(it)

                    newContentAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.newContentRecyclerView.visibility = View.VISIBLE

                } else {



                }

            })

            storefrontLiveData.categoriesItemData.observe(this@Storefront, {

                if (it.isNotEmpty()) {

                    categoriesAdapter.storefrontCategories.clear()
                    categoriesAdapter.storefrontCategories.addAll(it)

                    categoriesAdapter.notifyDataSetChanged()

                    storefrontLayoutBinding.categoriesRecyclerView.visibility = View.VISIBLE

                } else {



                }

            })

            storefrontLayoutBinding.nestedScrollView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {
                    Log.d(this@Storefront.javaClass.simpleName, "Scrolling Down")

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {
                    Log.d(this@Storefront.javaClass.simpleName, "Scrolling Up")

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

            storefrontLayoutBinding.categoriesRecyclerView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY) {
                    Log.d(this@Storefront.javaClass.simpleName, "Scrolling Down")

                    balloonOptionsMenu.removeBalloonOption()

                } else if (scrollY < oldScrollY) {
                    Log.d(this@Storefront.javaClass.simpleName, "Scrolling Up")

                    balloonOptionsMenu.removeBalloonOption()

                }

            }

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
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .remove(productDetailsFragment)
                .commitNow()

            prepareActionCenterUserInterface.setupIconsForStorefront()

            actionCenterOperations.setupForStorefront()

        } else if (categoryDetailsFragment.isShowing) {

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .remove(categoryDetailsFragment)
                .commitNow()

            prepareActionCenterUserInterface.setupIconsForStorefront()

            actionCenterOperations.setupForStorefront()

        } else {

            startActivity(Intent(Intent.ACTION_MAIN).apply {
                this.addCategory(Intent.CATEGORY_HOME)
                this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())

        }

    }

    override fun networkAvailable() {
        Log.d(this@Storefront.javaClass.simpleName, "Network Available @ ${this@Storefront.javaClass.simpleName}")

        retrieveFeaturedContent()

        retrieveAllContent()

        retrieveNewContent()

    }

    override fun networkLost() {
        Log.d(this@Storefront.javaClass.simpleName, "No Network @ ${this@Storefront.javaClass.simpleName}")

    }

}