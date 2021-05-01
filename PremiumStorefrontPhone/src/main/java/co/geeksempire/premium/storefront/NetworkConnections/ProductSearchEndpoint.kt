/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/1/21 8:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.NetworkConnections

class ProductSearchEndpoint (private val generalEndpoint: GeneralEndpoint) {

    val getAllProductsShowcaseEndpoint = "https://geeksempire.co/wp-json/wc/v3/products?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}&per_page=99&exclude=2341"

    fun getProductByIdEndpoint(productId: String) : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/${productId}" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

    fun getProductsSearchEndpoint(productSearchQuery: String) : String = "${getAllProductsShowcaseEndpoint}&search=$productSearchQuery"

    fun getFeaturedProductsEndpoint() : String = "${getAllProductsShowcaseEndpoint}&featured=true"

    fun getProductsCategoriesEndpoint() : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/categories" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

}