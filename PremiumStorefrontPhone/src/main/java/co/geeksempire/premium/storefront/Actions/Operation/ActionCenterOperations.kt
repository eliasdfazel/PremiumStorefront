/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/4/21, 10:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.Operation

import android.view.View
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.Data.shareApplication

class ActionCenterOperations (val context: Storefront) {

    fun setupForStorefront() {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            if (context.storefrontLayoutBinding.sortingInclude.root.isShown) {
                context.storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE
            }

            context.storefrontLayoutBinding.filteringInclude.root.visibility = if (context.storefrontLayoutBinding.filteringInclude.root.isShown) {
                View.GONE
            } else {
                View.VISIBLE
            }

//            context.filterAllContent.sortAllContentByInput(context.storefrontAllUnfilteredContents, SortingOptions.SortByRating)
//                .invokeOnCompletion {
//
//                }

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

//            context.filterAllContent.searchThroughAllContent(context.storefrontAllUnfilteredContents, "Float It")
//                .invokeOnCompletion {
//
//                }

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            if (context.storefrontLayoutBinding.filteringInclude.root.isShown) {
                context.storefrontLayoutBinding.filteringInclude.root.visibility = View.GONE
            }

            context.storefrontLayoutBinding.sortingInclude.root.visibility = if (context.storefrontLayoutBinding.sortingInclude.root.isShown) {
                View.GONE
            } else {
                View.VISIBLE
            }

//            context.filterAllContent.filterAlContentByInput(context.storefrontAllUnfilteredContents, FilteringOptions.FilterByCountry, "Germany")
//                .invokeOnCompletion {
//
//                }

        }

    }

    fun setupForProductDetails(applicationPackageName: String, applicationName: String, applicationSummary: String) {

        /* Share */
        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            shareApplication(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Install */
        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Rate */
        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

}