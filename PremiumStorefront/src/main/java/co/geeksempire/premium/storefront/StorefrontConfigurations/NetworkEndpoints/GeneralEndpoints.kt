/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 9/30/21, 7:47 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints

class GeneralEndpoints {

    val defaultProductsPerPage = 19
    val defaultNumberOfPage = 1

    /*
     * Firestore Endpoint
     */

    /**
     * /PremiumStorefront/Products
     **/
    val generalStorefrontDatabaseEndpoint = "/PremiumStorefront/Products"

    /**
     * /PremiumStorefront/Products/Android
     **/
    val generalStorefrontDatabaseAndroidEndpoint = generalStorefrontDatabaseEndpoint +
            "/" +
            "Android"

    /**
     * /PremiumStorefront/Products/QuickAccess
     **/
    fun firestoreQuickAccessEndpoint() : String =
        generalStorefrontDatabaseAndroidEndpoint +
            "/" +
            "QuickAccess"

    /*
     * Wordpress Endpoint
     */

    object QueryType {
        const val ApplicationsQuery = "80"
        const val GamesQuery = "546"
        const val MoviesQuery = "982"
    }

    private object Security {
        const val ConsumerKey = "ck_e469d717bd778da4fb9ec24881ee589d9b202662"
        const val ConsumerSecret = "cs_ac53c1b36d1a85e36a362855d83af93f0d377686"
    }

    val generalStorefrontEndpoint = "https://geeksempire.co/wp-json/wc/v3/"

    fun consumerKey(): String {

        return Security.ConsumerKey
    }

    fun consumerSecret(): String {

        return Security.ConsumerSecret
    }

    fun getProductByIdEndpoint(productId: String) : String = "${generalStorefrontEndpoint}" + "products/${productId}" + "?" +
            "consumer_key=${consumerKey()}" +
            "&" +
            "consumer_secret=${consumerSecret()}"

    fun getSeveralProductsByIdEndpoint(csvProductsId: String) : String = "${generalStorefrontEndpoint}" + "products" + "?" +
            "consumer_key=${consumerKey()}" +
            "&" +
            "consumer_secret=${consumerSecret()}" +
            "&" +
            "include=${csvProductsId}"

    fun getProductsSpecificCategoriesEndpoint(productCategoryId: Int = 67, productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) : String =
        "${generalStorefrontEndpoint}" + "products" + "?" +
                "consumer_key=${consumerKey()}" +
                "&" +
                "consumer_secret=${consumerSecret()}" +
                "&" +
                "category=${productCategoryId}" +
                "&" +
                "per_page=${productPerPage}" +
                "&" +
                "page=${numberOfPage}" +
                "&" +
                "orderby=price" +
                "&" +
                "order=desc"

    fun getProductsSpecificTagEndpoint(tagId: Int) : String =
        "${generalStorefrontEndpoint}" + "products" + "?" +
                "consumer_key=${consumerKey()}" +
                "&" +
                "consumer_secret=${consumerSecret()}" +
                "&tag=${tagId}"

}