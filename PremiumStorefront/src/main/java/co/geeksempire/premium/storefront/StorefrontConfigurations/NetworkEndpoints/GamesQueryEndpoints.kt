/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/2/21, 9:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints

class GamesQueryEndpoints (private val generalEndpoints: GeneralEndpoints) {


    val defaultProductsPerPage = 19
    val defaultNumberOfPage = 1

    /*
     * Firestore Endpoint
     */

    fun storefrontGamesCategoryEndpoint() : String = generalEndpoints.generalStorefrontDatabaseEndpoint +
            "/" +
            "Android" +
            "/" +
            "Games"

    fun firestoreSpecificGame(categoryName: String, applicationId: String) : String =
        generalEndpoints.generalStorefrontDatabaseAndroidEndpoint +
                "/" +
                "Games" +
                "/" +
                categoryName +
                "/" +
                applicationId

    fun storefrontFeaturedGamesEndpoint() : String  = storefrontGamesCategoryEndpoint() +
            "/" +
            "Featured" +
            "/" +
            "Content"

    fun firestoreGamesSpecificCategory(categoryName: String) : String = storefrontGamesCategoryEndpoint() +
            "/" +
            categoryName

    /*
     * Wordpress Endpoint
     */

    fun getAllAndroidGamesEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) =
        "https://geeksempire.co/wp-json/wc/v3/products?" +
                "consumer_key=${generalEndpoints.consumerKey()}" +
                "&" +
                "consumer_secret=${generalEndpoints.consumerSecret()}" +
                "&" +
                "per_page=${productPerPage}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "category=${GeneralEndpoints.QueryType.GamesQuery}" +
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
                "page=${numberOfPage}"

    fun getNewGamesEndpoint(numberOfProducts: Int = 3, numberOfPage: Int = 1) : String =
        "${getAllAndroidGamesEndpoint()}" +
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "page=${numberOfPage}" +
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
                "orderby=date" +
                "&" +
                "order=desc"

    fun getGamesCategoriesEndpoint(numberOfProducts: Int = 99, csvExclusions: String = "80,66,57,546") : String =
        "${generalEndpoints.generalStorefrontEndpoint}" + "products/categories" + "?" +
                "consumer_key=${generalEndpoints.consumerKey()}" +
                "&" +
                "consumer_secret=${generalEndpoints.consumerSecret()}" +
                "&" +
                "exclude=${csvExclusions}" + //Add Exclusion of All Other Categories
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "orderby=count" +
                "&" +
                "order=desc"

}