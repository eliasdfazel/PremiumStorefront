/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 8:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection

import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DevelopersDataKey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

interface DeveloperDataInterface {
    fun developerInformation() { }
}

class RetrieveDeveloperInformation (private val developerName: String) {

    val developerData = HashMap<String, String>()

    fun start(developerDataInterface: DeveloperDataInterface) {

        developerData.clear()

        DeveloperEndpoint()
            .developerDatabaseEndpoint(developerName)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    processDeveloperData(dataSnapshot)

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }

            })

    }

    fun processDeveloperData(dataSnapshot: DataSnapshot) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        dataSnapshot.children.forEach {

            if (it.key == DevelopersDataKey.DeveloperProducts) {

                //Application
                val applicationsString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperApplications).children.forEach { applications ->

                    applicationsString.append("${applications.value},")

                }

                developerData[DevelopersDataKey.DeveloperApplications] = applicationsString.toString()

                //Game
                val gamesString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperGames).children.forEach { product ->

                    gamesString.append("${product.value},")

                }

                developerData[DevelopersDataKey.DeveloperGames] = gamesString.toString()

                //Books
                val booksString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperBooks).children.forEach { product ->

                    booksString.append("${product.value},")

                }

                developerData[DevelopersDataKey.DeveloperBooks] = booksString.toString()

                //Movies
                val moviesString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperMovies).children.forEach { product ->

                    moviesString.append("${product.value},")

                }

                developerData[DevelopersDataKey.DeveloperMovies] = moviesString.toString()

            } else {

                developerData[it.key.toString()] = it.value.toString()

            }

        }

    }

}