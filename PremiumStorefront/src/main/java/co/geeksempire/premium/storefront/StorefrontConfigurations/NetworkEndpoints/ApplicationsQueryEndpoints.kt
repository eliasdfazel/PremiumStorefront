/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 4:46 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints

class ApplicationsQueryEndpoints (private val generalEndpoints: GeneralEndpoints) {

    val defaultProductsPerPage = 19
    val defaultNumberOfPage = 1

    /*
     * Firestore Endpoint
     */

    fun storefrontApplicationsCategoryEndpoint() : String = generalEndpoints.generalStorefrontDatabaseEndpoint +
            "/" +
            "Android" +
            "/" +
            "Applications"

    fun firestoreSpecificApplication(categoryName: String, applicationId: String) : String =
        generalEndpoints.generalStorefrontDatabaseAndroidEndpoint +
                "/" +
                "Applications" +
                "/" +
                categoryName +
                "/" +
                applicationId

    fun storefrontFeaturedApplicationsEndpoint() : String  = storefrontApplicationsCategoryEndpoint() +
            "/" +
            "Featured" +
            "/" +
            "Content"

    fun firestoreApplicationsSpecificCategory(categoryName: String) : String = storefrontApplicationsCategoryEndpoint() +
            "/" +
            categoryName

    /*
     * Wordpress Endpoint
     */

    fun getAllAndroidApplicationsEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) =
        "https://geeksempire.co/wp-json/wc/v3/products?" +
            "consumer_key=${generalEndpoints.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoints.consumerSecret()}" +
            "&" +
            "category=${GeneralEndpoints.QueryType.ApplicationsQuery}" +
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
                "&" +
                "search=$productSearchQuery" +
                "&" +
                "per_page=${productPerPage}" +
                "&" +
                "page=${numberOfPage}"

    fun getNewApplicationsEndpoint(numberOfProducts: Int = 3, numberOfPage: Int = 1) : String =
        "${getAllAndroidApplicationsEndpoint()}" +
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "orderby=date" +
                "&" +
                "order=desc"

    fun getOldApplicationsEndpoint(numberOfProducts: Int = 3, numberOfPage: Int = 1) : String =
        "${getAllAndroidApplicationsEndpoint()}" +
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "orderby=date" +
                "&" +
                "order=asc"

    fun getApplicationsCategoriesEndpoint(numberOfProducts: Int = 99, csvExclusions: String = "80,66,57,546") : String =
        "${generalEndpoints.generalStorefrontEndpoint}" + "products/categories" + "?" +
                "consumer_key=${generalEndpoints.consumerKey()}" +
                "&" +
                "consumer_secret=${generalEndpoints.consumerSecret()}" +
                "&" +
                "exclude=${csvExclusions}" + //Add Exclusion of All Other Categories
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "per_page=${numberOfProducts}" +
                "&" +
                "orderby=count" +
                "&" +
                "order=desc"

}