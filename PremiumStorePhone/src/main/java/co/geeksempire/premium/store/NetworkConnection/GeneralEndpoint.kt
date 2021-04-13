/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/12/21 5:38 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.store.NetworkConnection

class GeneralEndpoint {

    private object Security {
        const val ConsumerKey = "ck_e469d717bd778da4fb9ec24881ee589d9b202662"
        const val ConsumerSecret = "cs_ac53c1b36d1a85e36a362855d83af93f0d377686"
    }

    val getAllProductsShowcaseEndpoint = "https://geeksempire.co/storefront/wp-json/wc/v3/products?" +
            "consumer_key=${Security.ConsumerKey}" +
            "&" +
            "consumer_secret=${Security.ConsumerSecret}"

    fun getProductsSearchEndpoint(productSearchQuery: String): String = "$getAllProductsShowcaseEndpoint&search=$productSearchQuery"

}