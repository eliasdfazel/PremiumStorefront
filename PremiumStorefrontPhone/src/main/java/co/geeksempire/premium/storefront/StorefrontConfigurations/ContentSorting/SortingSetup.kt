/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 10:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.view.View
import android.view.animation.AnimationUtils
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.SortingOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

fun Storefront.sortingSetup() {

    if (storefrontLayoutBinding.filteringInclude.root.isShown) {

        storefrontLayoutBinding.filteringInclude.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE

    }

    if (storefrontLayoutBinding.sortingInclude.root.isShown) {
        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE
    } else {
        storefrontLayoutBinding.sortingInclude.root.visibility = View.VISIBLE
    }

    filterAllContent.sortAllContentByInput(allContentAdapter.storefrontContents, SortingOptions.SortByRating)
        .invokeOnCompletion {

        }

}