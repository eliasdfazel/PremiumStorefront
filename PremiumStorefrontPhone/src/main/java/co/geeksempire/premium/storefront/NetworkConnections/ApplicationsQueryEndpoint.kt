/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/27/21, 11:35 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.NetworkConnections

class ApplicationsQueryEndpoint (private val generalEndpoint: GeneralEndpoint) {

    val defaultProductsPerPage = 19
    val defaultNumberOfPage = 1

    fun getAllAndroidApplicationsEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) =
        "https://geeksempire.co/wp-json/wc/v3/products?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}" +
            "&" +
            "per_page=${productPerPage}" +
            "&" +
            "page=${numberOfPage}" +
            "&" +
            "category=${GeneralEndpoint.QueryType.ApplicationsQuery}" +
            "&" +
            "exclude=2341" +
            "&" +
            "orderby=date" +
            "&" +
            "order=desc"

    fun getFeaturedApplicationsEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) : String =
        getAllAndroidApplicationsEndpoint(productPerPage, numberOfPage) +
            "&" +
            "featured=true"

    fun getApplicationsSearchEndpoint(productSearchQuery: String = "1", productPerPage: Int = 99, numberOfPage: Int = 1) : String =
        "${getAllAndroidApplicationsEndpoint()}" +
            "&search=$productSearchQuery" +
            "&exclude=2341"

    fun getNewApplicationsEndpoint(numberOfProducts: Int = 3, productPerPage: Int = 99, numberOfPage: Int = 1) : String =
        "${getAllAndroidApplicationsEndpoint()}" +
            "&" +
            "per_page=${numberOfProducts}" +
            "&" +
            "exclude=2341" +
            "&" +
            "orderby=date" +
            "&" +
            "order=desc"

    fun getApplicationsCategoriesEndpoint(numberOfProducts: Int = 99, csvExclusions: String = "80,66,57,546") : String =
        "${generalEndpoint.generalStorefrontEndpoint}" + "products/categories" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}" +
            "&" +
            "exclude=${csvExclusions}" + //Add Exclusion of All Other Categories
            "&" +
            "per_page=${numberOfProducts}"

}