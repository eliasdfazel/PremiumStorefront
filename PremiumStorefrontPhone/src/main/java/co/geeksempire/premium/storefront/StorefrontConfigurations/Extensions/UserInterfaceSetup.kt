/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/29/21 5:42 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront


fun Storefront.setupUserInterface() {

    prepareActionCenterUserInterface.let {

        it.design()

        it.setupIconsForStorefront()

    }

}