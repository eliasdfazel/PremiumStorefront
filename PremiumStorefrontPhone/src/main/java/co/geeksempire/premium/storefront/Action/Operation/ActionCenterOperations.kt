/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/27/21 11:22 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Action.Operation

import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

class ActionCenterOperations (val context: Storefront) {

    fun setupForStorefront() {

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {



        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {



        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {



        }

    }

}