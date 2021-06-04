/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/4/21, 9:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.Operation

import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Filter.FilteringOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Filter.SortingOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.Data.shareApplication

class ActionCenterOperations (val context: Storefront) {

    fun setupForStorefront() {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            context.filterAllContent.sortAllContentByInput(context.storefrontAllUnfilteredContents, SortingOptions.SortByRating)
                .invokeOnCompletion {

                }

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            context.filterAllContent.searchThroughAllContent()

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            context.filterAllContent.filterAlContentByInput(context.storefrontAllUnfilteredContents, FilteringOptions.FilterByCountry, "Germany")
                .invokeOnCompletion {

                }

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