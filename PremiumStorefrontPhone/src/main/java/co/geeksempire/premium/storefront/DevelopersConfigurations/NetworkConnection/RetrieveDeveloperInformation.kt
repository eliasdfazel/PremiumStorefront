/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/17/21, 7:11 AM
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface DeveloperDataInterface {
    fun developerInformation(developerData: HashMap<String, String>) { }
}

class RetrieveDeveloperInformation (private val developerName: String) {

    private val developerData = HashMap<String, String>()

    fun start(developerDataInterface: DeveloperDataInterface) {

        developerData.clear()

        DeveloperEndpoint()
            .developerDatabaseEndpoint(developerName)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {

                        CoroutineScope(Dispatchers.IO).launch {

                            processDeveloperData(dataSnapshot).collect {

                                developerDataInterface.developerInformation(it)

                            }

                        }

                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }

            })

    }

    fun processDeveloperData(dataSnapshot: DataSnapshot) = flow<HashMap<String, String>> {

        dataSnapshot.children.forEach {

            if (it.key == DevelopersDataKey.DeveloperProducts) {

                //Application
                val applicationsString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperApplications).children.forEach { product ->

                    if (product.exists()) {
                        applicationsString.append("${product.value},")
                    }

                }

                developerData[DevelopersDataKey.DeveloperApplications] = applicationsString.toString()

                //Game
                val gamesString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperGames).children.forEach { product ->

                    if (product.exists()) {
                        gamesString.append("${product.value},")
                    }
                }

                developerData[DevelopersDataKey.DeveloperGames] = gamesString.toString()

                //Books
                val booksString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperBooks).children.forEach { product ->

                    if (product.exists()) {
                        booksString.append("${product.value},")
                    }

                }

                developerData[DevelopersDataKey.DeveloperBooks] = booksString.toString()

                //Movies
                val moviesString = StringBuilder()

                it.child(DevelopersDataKey.DeveloperMovies).children.forEach { product ->

                    if (product.exists()) {
                        moviesString.append("${product.value},")
                    }

                }

                developerData[DevelopersDataKey.DeveloperMovies] = moviesString.toString()

            } else {

                developerData[it.key.toString()] = it.value.toString()

            }

        }

        emit(developerData)

    }

}