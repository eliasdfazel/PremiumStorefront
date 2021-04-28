/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/27/21 10:34 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.FeaturedContent.Extensions

import co.geeksempire.premium.storefront.Action.View.PrepareActionCenterBackground
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront


fun Storefront.setupUserInterface() {

    PrepareActionCenterBackground(applicationContext, storefrontLayoutBinding.actionCenterView).start()

}