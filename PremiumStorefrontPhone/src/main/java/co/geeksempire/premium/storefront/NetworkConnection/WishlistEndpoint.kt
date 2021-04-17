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

class WishlistEndpoint (private val generalEndpoint: GeneralEndpoint) {

    fun getWishlistSharedKeyEndpoint(userId: String): String = generalEndpoint.generalStorefrontEndpoint + "wishlist/get_by_user/${userId}" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}"

    fun getWishlistProductsId(shareKey: String) : String = generalEndpoint.generalStorefrontEndpoint + "wishlist/${shareKey}/get_products"

}