/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/24/21, 9:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

object EnqueueEndPointQuery {
    const val JSON_REQUEST_TIMEOUT = (1000 * 3)
    const val JSON_REQUEST_RETRIES = (7)
}

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
                Log.d("JsonObjectRequestError", "${it?.networkResponse}")
                Log.d("JsonObjectRequestError", it?.networkResponse?.statusCode.toString())

                jsonRequestResponses.jsonRequestResponseFailureHandler(it?.networkResponse?.statusCode.toString())

            })

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                EnqueueEndPointQuery.JSON_REQUEST_TIMEOUT,
                EnqueueEndPointQuery.JSON_REQUEST_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        jsonObjectRequest.setShouldCache(true)

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)

    }

}