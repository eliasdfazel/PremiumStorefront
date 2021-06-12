/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 6:40 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.Operation

import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.filteringSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.searchingSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.sortingSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.Data.shareApplication

class ActionCenterOperations (val context: Storefront) {

    fun setupForStorefront(themeType: Boolean = ThemeType.ThemeLight) {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            context.sortingSetup(themeType)

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            context.searchingSetup(themeType)

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            context.filteringSetup(themeType)

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