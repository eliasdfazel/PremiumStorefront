/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/15/21, 2:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.NetworkConnections

class ProductSearchEndpoint (private val generalEndpoint: GeneralEndpoint) {

    fun getAllProductsShowcaseEndpoint(productPerPage: Int = 13, numberOfPage: Int = 1) = "https://geeksempire.co/wp-json/wc/v3/products?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}&per_page=${productPerPage}&page=${numberOfPage}&exclude=2341"

    fun getFeaturedProductsEndpoint(productPerPage: Int = 13, numberOfPage: Int = 1) : String = "${getAllProductsShowcaseEndpoint()}&featured=true"

    fun getProductByIdEndpoint(productId: String) : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/${productId}" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

    fun getProductsSearchEndpoint(productSearchQuery: String = "1", productPerPage: Int = 13, numberOfPage: Int = 1) : String = "${getAllProductsShowcaseEndpoint()}" +
            "&search=$productSearchQuery" +
            "&exclude=2341"

    fun getNewProductsEndpoint(numberOfProducts: Int = 3, productPerPage: Int = 13, numberOfPage: Int = 1) : String = "${getAllProductsShowcaseEndpoint()}" +
            "&per_page=${numberOfProducts}" +
            "&exclude=2341" +
            "&orderby=date" +
            "&order=desc"

    fun getProductsCategoriesEndpoint(numberOfProducts: Int = 99) : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products/categories" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}" +
            "&exclude=80,66,57" +
            "&per_page=${numberOfProducts}"

    fun getProductsSpecificCategoriesEndpoint(productCategoryId: Int = 67) : String = "${generalEndpoint.generalStorefrontEndpoint}" + "products" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}" +
            "&&category=${productCategoryId}"

}