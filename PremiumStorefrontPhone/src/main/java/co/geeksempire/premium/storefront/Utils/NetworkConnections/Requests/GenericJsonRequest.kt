/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/28/21 7:02 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests

import android.content.Context
import android.util.Log
import co.geeksempire.premium.storefront.BuildConfig
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations.EnqueueEndPointQuery
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley


class GenericJsonRequest(private val context: Context, private val jsonRequestResponses: JsonRequestResponses) {

    fun getMethod(endpointAddress: String) {

        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET,
            endpointAddress,
            null,
            { response ->
                Log.d("JsonObjectRequest ${this@GenericJsonRequest.javaClass.simpleName}", response.toString())

                    if (response != null) {

                        jsonRequestResponses.jsonRequestResponseSuccessHandler(response)

                    } else {



                    }

            }, {
                Log.d("JsonObjectRequestError", it?.networkResponse?.statusCode.toString())

                jsonRequestResponses.jsonRequestResponseFailureHandler(it?.networkResponse?.statusCode.toString())

            })

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                EnqueueEndPointQuery.JSON_REQUEST_TIMEOUT,
                EnqueueEndPointQuery.JSON_REQUEST_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        jsonObjectRequest.setShouldCache(BuildConfig.DEBUG)

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)

    }

}