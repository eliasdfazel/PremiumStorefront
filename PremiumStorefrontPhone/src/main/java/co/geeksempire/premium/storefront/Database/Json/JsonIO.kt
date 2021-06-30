/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/30/21, 10:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Json

import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import com.google.gson.Gson
import org.json.JSONArray

class JsonIO {

    private val jsonDatabase: Gson = JsonConfiguration().initialize()

    /**
     * Json Object
     **/
    fun writeMapToJson(inputData: Map<String, Any>) : JSONArray {

        return JSONArray(jsonDatabase.toJson(inputData))
    }

    fun writeArrayListToJson(inputData: ArrayList<StorefrontContentsData>) : JSONArray {

        return JSONArray(jsonDatabase.toJson(inputData))
    }

}