/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/17/21, 11:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Products.Applications

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.AllContent.Adapter.AllContentAdapter
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.Utils.UI.Colors.*
import co.geeksempire.premium.storefront.Utils.UI.SmoothScrollers.RecycleViewSmoothLayoutList
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import kotlinx.coroutines.*

class ApplicationsShowcase (private val context: AppCompatActivity,
                            private val productShowcaseRecyclerView: RecyclerView,
                            private val contentDetailsContainer: FragmentContainerView,
                            private val themeType: Boolean) : FragmentInterface {

    val productDetailsFragment: ProductDetailsFragment by lazy {
        ProductDetailsFragment()
    }

    val allContentAdapter: AllContentAdapter by lazy {
        AllContentAdapter(context = context,
            contentDetailsContainer = contentDetailsContainer,
            productDetailsFragment = productDetailsFragment,
            fragmentInterface = this@ApplicationsShowcase)
    }

    fun prepareToPresent(applicationsList: ArrayList<StorefrontContentsData>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        allContentAdapter.storefrontContents.clear()

        allContentAdapter.storefrontContents.addAll(applicationsList)

        withContext(Dispatchers.Main) {

            productShowcaseRecyclerView.layoutManager = RecycleViewSmoothLayoutList(context, RecyclerView.VERTICAL, false)

            allContentAdapter.themeType = this@ApplicationsShowcase.themeType

            productShowcaseRecyclerView.adapter = allContentAdapter
            allContentAdapter.notifyDataSetChanged()

        }

    }

}