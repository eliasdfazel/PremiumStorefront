/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/11/21, 7:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.databinding.CategoryDetailsLayoutBinding

class CategoryDetailsFragment : Fragment() {

    var isShowing = false

    private lateinit var categoryDetailsLayoutBinding: CategoryDetailsLayoutBinding

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




    }

    override fun onDestroyView() {
        super.onDestroyView()

        isShowing = false

    }

}