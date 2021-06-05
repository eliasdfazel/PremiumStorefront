/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 4:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront

fun Storefront.searchingSetup() {

    filterAllContent.searchThroughAllContent(storefrontAllUnfilteredContents, "Float It")
        .invokeOnCompletion {

        }

}