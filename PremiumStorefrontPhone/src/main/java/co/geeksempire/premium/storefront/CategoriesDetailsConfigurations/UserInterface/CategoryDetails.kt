/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/19/21, 9:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.Extensions.setupCategoryDetailsUserInterface
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.NetworkOperations.ProductsOfCategory
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.ProductsOfCategoryAdapter
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
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
        ProductsOfCategory(applicationContext, productsOfCategoryAdapter, categoryDetailsLayoutBinding.loadingView)
    }

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    val productsOfCategoryAdapter: ProductsOfCategoryAdapter by lazy {
        ProductsOfCategoryAdapter(this@CategoryDetails)
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

        }

        categoryDetailsLayoutBinding.goBackView.setOnClickListener {

            this@CategoryDetails.finish()

            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_out_right)

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

        } else {

            this@CategoryDetails.finish()

            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_out_right)

        }

    }

    override fun networkAvailable() {
        Log.d(this@CategoryDetails.javaClass.simpleName, "Network Available @ ${this@CategoryDetails.javaClass.simpleName}")

        intent?.let { inputData ->

            val categoryId = inputData.getIntExtra(CategoriesDataKeys.CategoryId, 15)

            productsOfCategory.retrieveProductsOfCategory(categoryId)

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

    }

    override fun fragmentDestroyed() {
        super.fragmentDestroyed()

        categoryDetailsLayoutBinding.categoryIconImageView.visibility = View.VISIBLE
        categoryDetailsLayoutBinding.categoryNameTextView.visibility = View.VISIBLE

    }

}