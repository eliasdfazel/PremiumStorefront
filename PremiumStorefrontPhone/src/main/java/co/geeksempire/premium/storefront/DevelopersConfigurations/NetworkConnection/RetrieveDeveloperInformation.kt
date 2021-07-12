/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 6:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

interface DeveloperDataInterface {
    fun developerInformation() {}
}

data class DeveloperData  (var developerName: String, var developerDescription: String, var developerLogo: String, var developerCoverImage: String,
    var productsApplications: ArrayList<Int>, var productsGames: ArrayList<Int>, var productsMovies: ArrayList<Int>, var productsBooks: ArrayList<Int>)

class RetrieveDeveloperInformation (private val developerName: String) {

    fun start(developerDataInterface: DeveloperDataInterface) {

        DeveloperEndpoint()
            .developerDatabaseEndpoint(developerName)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    dataSnapshot.children.forEach {

//                        val developerName = it.get

                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }

            })

    }

}