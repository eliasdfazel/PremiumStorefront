/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 10:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.Extensions.setupCategoryDetailsUserInterface
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.NetworkOperations.retrieveProductsOfCategory
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.ProductsOfCategoryAdapter
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.databinding.CategoryDetailsLayoutBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryDetails : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@CategoryDetails)
    }

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    val productsOfCategoryAdapter: ProductsOfCategoryAdapter by lazy {
        ProductsOfCategoryAdapter(this@CategoryDetails)
    }

    var isShowing = false

    lateinit var categoryDetailsLayoutBinding: CategoryDetailsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryDetailsLayoutBinding = CategoryDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(categoryDetailsLayoutBinding.root)

        intent?.let { inputData ->

            categoryDetailsLayoutBinding.productsOfCategoryRecyclerView.layoutManager = RecycleViewSmoothLayoutGrid(applicationContext, columnCount(applicationContext, 307), RecyclerView.VERTICAL,false)
            categoryDetailsLayoutBinding.productsOfCategoryRecyclerView.adapter = productsOfCategoryAdapter

            lifecycleScope.launch {

                themePreferences.checkThemeLightDark().collect {

                    setupCategoryDetailsUserInterface(it)

                    productsOfCategoryAdapter.themeType = it

                }

            }

            retrieveProductsOfCategory(inputData.getLongExtra(CategoriesDataKeys.CategoryId, 67))

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

    override fun onBackPressed() {

        this@CategoryDetails.finish()

        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_out_right)

    }

}