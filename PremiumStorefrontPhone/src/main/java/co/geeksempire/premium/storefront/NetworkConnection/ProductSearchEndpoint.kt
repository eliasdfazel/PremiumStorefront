/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/17/21 1:36 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.NetworkConnection

class ProductSearchEndpoint (private val generalEndpoint: GeneralEndpoint) {

    val getAllProductsShowcaseEndpoint = "https://geeksempire.co/wp-json/wc/v3/products?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

    fun getProductByIdEndpoint(productId: String) : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/${productId}" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

    fun getProductsSearchEndpoint(productSearchQuery: String): String = "${getAllProductsShowcaseEndpoint}&search=$productSearchQuery"

    fun getFeaturedProductsEndpoint(productSearchQuery: String): String = "${getAllProductsShowcaseEndpoint}&featured=true"

}