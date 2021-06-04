/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/4/21, 9:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.Operation

import android.view.View
import android.view.animation.AnimationUtils
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.Filter.SortingOptions
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.Data.shareApplication

class ActionCenterOperations (val context: Storefront) {

    fun setupForStorefront() {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            context.storefrontLayoutBinding.loadingView.visibility = View.VISIBLE
            context.storefrontLayoutBinding.loadingView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))

            context.filterAllContent.sortAllContentByInput(context.storefrontAllUnfilteredContents, SortingOptions.SortByRating)
                .invokeOnCompletion {

                    context.storefrontLayoutBinding.loadingView.visibility = View.INVISIBLE
                    context.storefrontLayoutBinding.loadingView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))

                }

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            context.filterAllContent.searchThroughAllContent()

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            context.filterAllContent.filterAlContentByInput()

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