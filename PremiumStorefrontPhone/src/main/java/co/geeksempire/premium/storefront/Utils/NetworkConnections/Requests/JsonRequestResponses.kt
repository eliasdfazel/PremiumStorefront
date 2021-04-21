/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/21/21 12:00 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

interface JsonRequestResponses {
    fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
        Log.d(this@JsonRequestResponses.javaClass.simpleName, rawDataJsonArray.toString())
    }

    fun jsonRequestResponseSuccessHandler(rawDataJsonObject: JSONObject) {
        Log.d(this@JsonRequestResponses.javaClass.simpleName, rawDataJsonObject.toString())

    }

    fun jsonRequestResponseFailureHandler(jsonError: String?) {
        Log.d(this@JsonRequestResponses.javaClass.simpleName, jsonError.toString())

    }

    fun jsonRequestResponseFailureHandler(networkError: Int?) {
        Log.d(this@JsonRequestResponses.javaClass.simpleName, networkError.toString())

    }
}