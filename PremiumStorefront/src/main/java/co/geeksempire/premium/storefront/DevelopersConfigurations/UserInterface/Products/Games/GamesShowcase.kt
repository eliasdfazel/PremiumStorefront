/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/13/21, 6:26 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Products.Games

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.DeveloperContents.Adapter.AllDeveloperContentsAdapter
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import kotlinx.coroutines.*

class GamesShowcase (private val context: AppCompatActivity,
                     private val productShowcaseRecyclerView: RecyclerView,
                     private val contentDetailsContainer: FragmentContainerView,
                     private val productDetailsFragment: ProductDetailsFragment,
                     private val fragmentInterface: FragmentInterface,
                     private val themeType: Boolean) {

    val allContentAdapter: AllDeveloperContentsAdapter by lazy {
        AllDeveloperContentsAdapter(context = context,
            contentDetailsContainer = contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = fragmentInterface)
    }

    fun prepareToPresent(applicationsList: ArrayList<StorefrontContentsData>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        allContentAdapter.storefrontContents.clear()

        allContentAdapter.storefrontContents.addAll(applicationsList)

        withContext(Dispatchers.Main) {

            productShowcaseRecyclerView.layoutManager = RecycleViewSmoothLayoutList(context, RecyclerView.VERTICAL, false)

            allContentAdapter.themeType = this@GamesShowcase.themeType

            productShowcaseRecyclerView.adapter = allContentAdapter
            allContentAdapter.notifyDataSetChanged()

        }

    }

}