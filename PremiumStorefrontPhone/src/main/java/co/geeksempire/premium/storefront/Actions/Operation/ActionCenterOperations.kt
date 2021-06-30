/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/30/21, 8:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.Operation

import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.filteringSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.searchingSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions.sortingSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.Data.shareApplication

class ActionCenterOperations {

    /*
     * Applications
     */
    fun setupForApplicationsStorefront(context: StorefrontApplications, themeType: Boolean = ThemeType.ThemeLight) {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            sortingSetup(context = context, filterAllContent = context.filterAllContent,
                context.storefrontLayoutBinding.sortingInclude, filteringInclude = context.storefrontLayoutBinding.filteringInclude,
                rightActionView = context.storefrontLayoutBinding.rightActionView, middleActionView =  context.storefrontLayoutBinding.middleActionView, leftActionView = context.storefrontLayoutBinding.leftActionView, context.allContentAdapter,
                themeType = themeType)

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            context.searchingSetup(themeType)

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            context.filteringSetup(themeType)

        }

    }

    fun setupForApplicationsDetails(context: StorefrontApplications, applicationPackageName: String, applicationName: String, applicationSummary: String) {

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

    /*
     * Games
     */
    fun setupForGamesStorefront(context: StorefrontGames, themeType: Boolean = ThemeType.ThemeLight) {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            sortingSetup(context = context, filterAllContent = context.filterAllContent,
                context.storefrontLayoutBinding.sortingInclude, filteringInclude = context.storefrontLayoutBinding.filteringInclude,
                rightActionView = context.storefrontLayoutBinding.rightActionView, middleActionView =  context.storefrontLayoutBinding.middleActionView, leftActionView = context.storefrontLayoutBinding.leftActionView, context.allContentAdapter,
                themeType = themeType)

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

//            context.searchingSetup(themeType)

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

//            context.filteringSetup(themeType)

        }

    }

    fun setupForGamesDetails(context: StorefrontGames, applicationPackageName: String, applicationName: String, applicationSummary: String) {

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