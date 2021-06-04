/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/4/21, 11:05 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.view.View
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

fun Storefront.filteringSetup() {

    if (storefrontLayoutBinding.filteringInclude.root.isShown) {
        storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE
    }

    storefrontLayoutBinding.sortingInclude.root.visibility = if (storefrontLayoutBinding.sortingInclude.root.isShown) {
        View.GONE
    } else {
        View.VISIBLE
    }

//            context.filterAllContent.filterAlContentByInput(context.storefrontAllUnfilteredContents, FilteringOptions.FilterByCountry, "Germany")
//                .invokeOnCompletion {
//
//                }

}