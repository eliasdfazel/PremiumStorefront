/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 6:34 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.GeneralConfigurations

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealtimeDatabaseConfiguration {

    private val realtimeDatabase = Firebase.database

    val realtimeDatabaseReference: DatabaseReference = realtimeDatabase.reference

    init {

        realtimeDatabase.goOnline()

    }

    fun forceReadCache() {

        realtimeDatabase.goOffline()

    }

    fun forceReadInternet() {

        realtimeDatabase.goOnline()

    }

}