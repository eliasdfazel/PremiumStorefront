/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/3/21 7:03 AM
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

    fun getProductsSearchEndpoint(productSearchQuery: String = "1") : String = "${getAllProductsShowcaseEndpoint}" +
            "&search=$productSearchQuery" +
            "&exclude=2341"

    fun getNewProductsEndpoint(numberOfProducts: Int = 3) : String = "${getAllProductsShowcaseEndpoint}" +
            "&per_page=${numberOfProducts}" +
            "&exclude=2341" +
            "&orderby=date" +
            "&order=desc"

    fun getProductsCategoriesEndpoint(numberOfProducts: Int = 99) : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/categories" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}" + "&per_page=${numberOfProducts}"

}