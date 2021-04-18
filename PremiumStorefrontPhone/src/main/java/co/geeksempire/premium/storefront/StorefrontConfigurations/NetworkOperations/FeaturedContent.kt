/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/18/21 12:22 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import android.util.Log
import co.geeksempire.premium.storefront.NetworkConnections.ProductSearchEndpoint
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

object EnqueueEndPointQuery {
    const val JSON_REQUEST_TIMEOUT = (1000 * 3)
    const val JSON_REQUEST_RETRIES = (3)
}

fun Storefront.retrieveFeaturedContent() {

    val productSearchEndpoint: ProductSearchEndpoint = ProductSearchEndpoint(generalEndpoint)

    val jsonObjectRequest = JsonArrayRequest(
        Request.Method.GET,
        productSearchEndpoint.getFeaturedProductsEndpoint(),
        null,
        { response ->
            Log.d("JsonObjectRequest ${this@retrieveFeaturedContent.javaClass.simpleName}", response.toString())

            if (response != null) {



            }

        }, {
            Log.d("JsonObjectRequestError", it?.networkResponse?.statusCode.toString())

        })

    jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
        EnqueueEndPointQuery.JSON_REQUEST_TIMEOUT,
        EnqueueEndPointQuery.JSON_REQUEST_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )

    jsonObjectRequest.setShouldCache(false)

    val requestQueue = Volley.newRequestQueue(applicationContext)
    requestQueue.add(jsonObjectRequest)

}