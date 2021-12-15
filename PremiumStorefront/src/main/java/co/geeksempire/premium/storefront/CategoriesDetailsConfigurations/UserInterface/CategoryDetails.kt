/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 6:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Actions.Operation.ActionCenterOperations
import co.geeksempire.premium.storefront.Actions.View.PrepareActionCenterUserInterface
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.Extensions.setupCategoryDetailsUserInterface
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.NetworkOperations.ProductsOfCategory
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.ProductsOfCategoryAdapter
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.UniqueRecommendationsCategoryAdapter
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import co.geeksempire.premium.storefront.databinding.CategoryDetailsLayoutBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryDetails : AppCompatActivity(), NetworkConnectionListenerInterface, FragmentInterface {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@CategoryDetails)
    }

    val productsOfCategory: ProductsOfCategory by lazy {
        ViewModelProvider(this@CategoryDetails)[ProductsOfCategory::class.java]
    }

    val generalEndpoints: GeneralEndpoints = GeneralEndpoints()

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    val productsOfCategoryAdapter: ProductsOfCategoryAdapter by lazy {
        ProductsOfCategoryAdapter(this@CategoryDetails)
    }

    val uniqueRecommendationsCategoryAdapter: UniqueRecommendationsCategoryAdapter by lazy {
        UniqueRecommendationsCategoryAdapter(this@CategoryDetails)
    }

    val prepareActionCenterUserInterface: PrepareActionCenterUserInterface by lazy {
        PrepareActionCenterUserInterface(context = applicationContext, actionCenterView = categoryDetailsLayoutBinding.actionCenterView, actionLeftView = categoryDetailsLayoutBinding.leftActionView, actionMiddleView = categoryDetailsLayoutBinding.middleActionView, actionRightView = categoryDetailsLayoutBinding.rightActionView)
    }

    val actionCenterOperations: ActionCenterOperations by lazy {
        ActionCenterOperations()
    }

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@CategoryDetails, categoryDetailsLayoutBinding.rootView, networkCheckpoint)
    }

    var isShowing = false

    lateinit var categoryDetailsLayoutBinding: CategoryDetailsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryDetailsLayoutBinding = CategoryDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(categoryDetailsLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@CategoryDetails

        intent?.let { inputData ->

            categoryDetailsLayoutBinding.productsOfCategoryRecyclerView.layoutManager = RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)
            categoryDetailsLayoutBinding.productsOfCategoryRecyclerView.adapter = productsOfCategoryAdapter

            categoryDetailsLayoutBinding.uniqueRecyclerView.layoutManager = RecycleViewSmoothLayoutList(applicationContext, RecyclerView.HORIZONTAL,false)
            categoryDetailsLayoutBinding.uniqueRecyclerView.adapter = uniqueRecommendationsCategoryAdapter

            lifecycleScope.launch {

                themePreferences.checkThemeLightDark().collect {

                    setupCategoryDetailsUserInterface(it)

                    productsOfCategoryAdapter.themeType = it

                }

            }

            categoryDetailsLayoutBinding.categoryNameTextView.text = inputData.getStringExtra(CategoriesDataKeys.CategoryName)

            Glide.with(applicationContext)
                .load(inputData.getStringExtra(CategoriesDataKeys.CategoryIcon))
                .into(categoryDetailsLayoutBinding.categoryIconImageView)

            productsOfCategory.uniqueRecommendationsProducts.observe(this@CategoryDetails, {

                if (categoryDetailsLayoutBinding.uniqueRecyclerView.isGone) {

                    categoryDetailsLayoutBinding.uniqueRecyclerView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    categoryDetailsLayoutBinding.uniqueRecyclerView.visibility = View.VISIBLE

                    categoryDetailsLayoutBinding.leftBlurView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    categoryDetailsLayoutBinding.leftBlurView.visibility = View.VISIBLE

                    categoryDetailsLayoutBinding.rightBlurView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    categoryDetailsLayoutBinding.rightBlurView.visibility = View.VISIBLE

                    categoryDetailsLayoutBinding.leftGlowView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    categoryDetailsLayoutBinding.leftGlowView.visibility = View.VISIBLE

                    categoryDetailsLayoutBinding.rightGlowView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    categoryDetailsLayoutBinding.rightGlowView.visibility = View.VISIBLE

                    categoryDetailsLayoutBinding.uniqueRecommendationTextView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    categoryDetailsLayoutBinding.uniqueRecommendationTextView.visibility = View.VISIBLE

                }

            })

        }

        categoryDetailsLayoutBinding.goBackView.setOnClickListener {

            this@CategoryDetails.finish()

            overridePendingTransition(0, R.anim.slide_out_right)

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
                .detach(productDetailsFragment)
                .commitNow()

        } else {

            this@CategoryDetails.finish()

            overridePendingTransition(0, R.anim.slide_out_right)

        }

    }

    override fun networkAvailable() {
        Log.d(this@CategoryDetails.javaClass.simpleName, "Network Available @ ${this@CategoryDetails.javaClass.simpleName}")

        intent?.let { inputData ->

            val categoryId = inputData.getIntExtra(CategoriesDataKeys.CategoryId, 15)
            val categoryName = inputData.getStringExtra(CategoriesDataKeys.CategoryName)?:"Productivity"

            val queryType = inputData.getStringExtra(GeneralEndpoints.QueryType.QueryTypeCase)?:GeneralEndpoints.QueryType.ApplicationsQuery

            productsOfCategory.retrieveProductsOfCategory(this@CategoryDetails, categoryId, categoryName.split(" ").first(), productsOfCategoryAdapter, uniqueRecommendationsCategoryAdapter, categoryDetailsLayoutBinding.loadingView, queryType)

            Log.d(this@CategoryDetails.javaClass.simpleName, "Category Id $categoryId")
        }

    }

    override fun networkLost() {
        Log.d(this@CategoryDetails.javaClass.simpleName, "No Network @ ${this@CategoryDetails.javaClass.simpleName}")

    }

    override fun fragmentCreated(applicationPackageName: String, applicationName: String, applicationSummary: String) {
        super.fragmentCreated(applicationPackageName, applicationName, applicationSummary)

        categoryDetailsLayoutBinding.categoryIconImageView.visibility = View.INVISIBLE
        categoryDetailsLayoutBinding.categoryNameTextView.visibility = View.INVISIBLE

        actionCenterOperations.setupCategoryVisibility(this@CategoryDetails)

        actionCenterOperations.setupForCategoryDetails(context = this@CategoryDetails,
            applicationPackageName = applicationPackageName?:packageName,
            applicationName = applicationName?:getString(R.string.applicationName),
            applicationSummary = applicationSummary?:getString(R.string.applicationSummary))

        lifecycleScope.launch {
            themePreferences.checkThemeLightDark().collect {

                prepareActionCenterUserInterface.setupIconsForDetails(it)

            }
        }

    }

    override fun fragmentDestroyed() {
        super.fragmentDestroyed()

        categoryDetailsLayoutBinding.categoryIconImageView.visibility = View.VISIBLE
        categoryDetailsLayoutBinding.categoryNameTextView.visibility = View.VISIBLE

        actionCenterOperations.setupCategoryVisibility(this@CategoryDetails)

    }

}