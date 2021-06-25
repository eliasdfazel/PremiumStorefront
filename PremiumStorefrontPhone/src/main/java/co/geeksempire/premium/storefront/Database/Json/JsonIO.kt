/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 7:30 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Json

import com.google.gson.Gson
import org.json.JSONObject

class JsonIO {

    private val jsonDatabase: Gson = JsonConfiguration().initialize()

    /**
     * Json Object
     **/
    fun writeMapToJson(inputData: Map<String, Any>) : JSONObject {

        return JSONObject(jsonDatabase.toJson(inputData))
    }

}