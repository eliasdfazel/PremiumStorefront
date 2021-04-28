/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/28/21 12:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Extensions

import co.geeksempire.premium.storefront.Action.View.PrepareActionCenterBackground
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront


fun Storefront.setupUserInterface() {

    PrepareActionCenterBackground(context = applicationContext, actionCenterView = storefrontLayoutBinding.actionCenterView,
            actionLeftView = storefrontLayoutBinding.leftActionView, actionMiddleView = storefrontLayoutBinding.middleActionView, actionRightView = storefrontLayoutBinding.rightActionView).start()

}