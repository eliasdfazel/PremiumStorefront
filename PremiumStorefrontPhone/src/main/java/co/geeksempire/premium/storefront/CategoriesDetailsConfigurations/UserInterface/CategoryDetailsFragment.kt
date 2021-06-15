/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 9:12 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.NetworkOperations.retrieveProductsOfCategory
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.ProductsOfCategoryAdapter
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.UI.Display.columnCount
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutGrid
import co.geeksempire.premium.storefront.databinding.CategoryDetailsLayoutBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryDetailsFragment : Fragment() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(requireActivity() as AppCompatActivity)
    }

    val generalEndpoint: GeneralEndpoint = GeneralEndpoint()

    val productsOfCategoryAdapter: ProductsOfCategoryAdapter by lazy {
        ProductsOfCategoryAdapter(requireActivity() as Storefront)
    }

    var isShowing = false

    lateinit var categoryDetailsLayoutBinding: CategoryDetailsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as Storefront).prepareActionCenterUserInterface.setupIconsForCategoryDetails()

    }

    override fun onCreateView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        categoryDetailsLayoutBinding = CategoryDetailsLayoutBinding.inflate(layoutInflater)

        return categoryDetailsLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { inputData ->

            categoryDetailsLayoutBinding.productsOfCategoryRecyclerView.layoutManager = RecycleViewSmoothLayoutGrid(requireContext(), columnCount(requireContext(), 307), RecyclerView.VERTICAL,false)
            categoryDetailsLayoutBinding.productsOfCategoryRecyclerView.adapter = productsOfCategoryAdapter

            lifecycleScope.launch {

                themePreferences.checkThemeLightDark().collect {

                    productsOfCategoryAdapter.themeType = it

                }

            }

            retrieveProductsOfCategory(inputData.getLong(CategoriesDataKeys.CategoryId))

            categoryDetailsLayoutBinding.categoryNameTextView.text = inputData.getString(CategoriesDataKeys.CategoryName)

            Glide.with(requireContext())
                .load(inputData.getString(CategoriesDataKeys.CategoryIcon))
                .into(categoryDetailsLayoutBinding.categoryIconImageView)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        isShowing = false

    }

}