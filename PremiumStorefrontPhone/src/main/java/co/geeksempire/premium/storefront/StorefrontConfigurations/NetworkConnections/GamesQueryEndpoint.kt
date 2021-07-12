/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 6:02 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections

class GamesQueryEndpoint (private val generalEndpoint: GeneralEndpoint) {

    val defaultProductsPerPage = 19
    val defaultNumberOfPage = 1

    fun getAllAndroidGamesEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) =
        "https://geeksempire.co/wp-json/wc/v3/products?" +
                "consumer_key=${generalEndpoint.consumerKey()}" +
                "&" +
                "consumer_secret=${generalEndpoint.consumerSecret()}" +
                "&" +
                "per_page=${productPerPage}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "category=${GeneralEndpoint.QueryType.GamesQuery}" +
                "&" +
                "exclude=2341" +
                "&" +
                "orderby=date" +
                "&" +
                "order=desc"

    fun getFeaturedGamesEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) : String =
        getAllAndroidGamesEndpoint(productPerPage, numberOfPage) +
                "&" +
                "featured=true"

    fun getGamesSearchEndpoint(productSearchQuery: String = "1", productPerPage: Int = 99, numberOfPage: Int = 1) : String =
        "${getAllAndroidGamesEndpoint()}" +
                "&search=$productSearchQuery" +
                "&" +
                "per_page=${productPerPage}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "exclude=2341"

    fun getNewGamesEndpoint(numberOfProducts: Int = 3, numberOfPage: Int = 1) : String =
        "${getAllAndroidGamesEndpoint()}" +
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "exclude=2341" +
                "&" +
                "orderby=date" +
                "&" +
                "order=desc"

    fun getOldGamesEndpoint(numberOfProducts: Int = 3, numberOfPage: Int = 1) : String =
        "${getAllAndroidGamesEndpoint()}" +
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "exclude=2341" +
                "&" +
                "orderby=date" +
                "&" +
                "order=desc"

    fun getGamesCategoriesEndpoint(numberOfProducts: Int = 99, csvExclusions: String = "80,66,57,546") : String =
        "${generalEndpoint.generalStorefrontEndpoint}" + "products/categories" + "?" +
                "consumer_key=${generalEndpoint.consumerKey()}" +
                "&" +
                "consumer_secret=${generalEndpoint.consumerSecret()}" +
                "&" +
                "exclude=${csvExclusions}" + //Add Exclusion of All Other Categories
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "orderby=count" +
                "&" +
                "order=desc"

}