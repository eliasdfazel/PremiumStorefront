/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/20/21, 9:44 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.Operation

import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall

class ActionCenterOperations (val context: Storefront) {

    fun setupForStorefront() {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {



        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {



        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {



        }

    }

    fun setupForProductDetails(applicationPackageName: String, applicationName: String, applicationSummary: String) {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {



        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {



        }

    }

}