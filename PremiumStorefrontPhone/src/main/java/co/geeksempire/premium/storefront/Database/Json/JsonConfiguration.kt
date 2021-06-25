/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 7:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Json

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class JsonConfiguration {

    fun initialize() : Gson {

        return GsonBuilder()
            .setPrettyPrinting()
            .create()
    }
}