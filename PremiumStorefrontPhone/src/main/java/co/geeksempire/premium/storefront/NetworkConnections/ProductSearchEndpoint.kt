/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/3/21 1:30 AM
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

    fun getFeaturedProductsEndpoint() : String = "${getAllProductsShowcaseEndpoint}&featured=true"

    fun getProductByIdEndpoint(productId: String) : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/${productId}" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

    fun getProductsSearchEndpoint(productSearchQuery: String) : String = "${getAllProductsShowcaseEndpoint}" +
            "&search=$productSearchQuery" +
            "&exclude=2341"

    fun getNewProductsEndpoint(numberOfProducts: String) : String = "${getAllProductsShowcaseEndpoint}" +
            "&per_page=${numberOfProducts}" +
            "&exclude=2341" +
            "&orderby=date" +
            "&order=asc"

    fun getProductsCategoriesEndpoint() : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/categories" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

}