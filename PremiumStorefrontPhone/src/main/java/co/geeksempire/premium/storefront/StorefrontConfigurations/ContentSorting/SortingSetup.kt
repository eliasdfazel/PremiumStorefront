/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 10:07 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.view.View
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.SortingOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

fun Storefront.sortingSetup() {

    if (storefrontLayoutBinding.filteringInclude.root.isShown) {
        storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE
    }

//    storefrontLayoutBinding.sortingInclude.root.visibility = if (storefrontLayoutBinding.sortingInclude.root.isShown) {
//        View.GONE
//    } else {
//        View.VISIBLE
//    }

    filterAllContent.sortAllContentByInput(allContentAdapter.storefrontContents, SortingOptions.SortByRating)
        .invokeOnCompletion {

        }

}