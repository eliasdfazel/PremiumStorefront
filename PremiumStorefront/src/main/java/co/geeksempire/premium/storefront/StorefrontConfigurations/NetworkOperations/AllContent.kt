/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/12/21, 6:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkOperations

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.CategoriesIds
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.CategoryDataKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontLiveData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.IO.IO
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import org.json.JSONArray
import java.io.File
import java.nio.charset.Charset

class AllContent (val context: AppCompatActivity,
                  val storefrontLiveData: StorefrontLiveData,
                  val queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

    private val generalEndpoint = GeneralEndpoints()

    private val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoint)

    private val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoint)

    private var numberOfPageToRetrieve: Int = 1

    var allLoadingFinished: Boolean = false

    fun retrieveAllApplicationsContent() {

        val applicationsDocuments = ArrayList<List<DocumentSnapshot>>()

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .document(applicationsQueryEndpoints.storefrontApplicationsCategoryEndpoint())
            .get(Source.DEFAULT).addOnSuccessListener { documentSnapshot ->

                if (documentSnapshot.exists()) {

                    val categoriesCollection = documentSnapshot.toObject(CategoriesIds::class.java)!!.CategoriesIds

                    categoriesCollection?.forEach { documentMap ->

                        val categoryId = documentMap[CategoryDataKey.CategoryId].toString().toInt()

                        val categoryName = documentMap[CategoryDataKey.CategoryName].toString()
                        val categoryIconLink = documentMap[CategoryDataKey.CategoryIconLink].toString()

                        val productCount = documentMap[CategoryDataKey.ProductCount].toString().toInt()

                        if (categoryName != "All") {
                            Log.d(this@AllContent.javaClass.simpleName, applicationsQueryEndpoints.firestoreApplicationsSpecificCategory(categoryName))

                            (context.application as PremiumStorefrontApplication)
                                .firestoreDatabase
                                .collection(applicationsQueryEndpoints.firestoreApplicationsSpecificCategory(categoryName))
                                .get(Source.DEFAULT).addOnSuccessListener { querySnapshot ->

                                    if (querySnapshot.documents.isNotEmpty()) {

                                        applicationsDocuments.add(querySnapshot.documents)

                                        if ((categoriesCollection.size - 1) == applicationsDocuments.size) {



                                        }

                                    }

                                }.addOnFailureListener {



                                }

                        }

                    }

                }

            }.addOnFailureListener {
                it.printStackTrace()

            }
        //Get Applications Inside Each Categories Directory

    }

    fun retrieveAllGamesContent() {

        //Get All Categories
        //Get Games Inside Each Categories Directory

        retrieveAllContentWordpress()

    }

    fun retrieveAllContentWordpress() {

        val allContentFile: File = when (queryType) {
            GeneralEndpoints.QueryType.ApplicationsQuery -> {

                context.getFileStreamPath(IO.UpdateApplicationsDataKey)

            }
            GeneralEndpoints.QueryType.GamesQuery -> {

                context.getFileStreamPath(IO.UpdateGamesDataKey)

            }
            else -> context.getFileStreamPath(IO.UpdateApplicationsDataKey)
        }

        if (allContentFile.exists()) {
            Log.d(this@AllContent.javaClass.simpleName, "Offline Data Available")

            val offlineData = JSONArray(allContentFile.readText(Charset.defaultCharset()))

            storefrontLiveData.processAllContentOfflineWordpress(offlineData).invokeOnCompletion {

                allLoadingFinished = true

            }

        } else {

            val endpoint = when (queryType) {
                GeneralEndpoints.QueryType.ApplicationsQuery -> {

                    applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint()

                }
                GeneralEndpoints.QueryType.GamesQuery -> {

                    gamesQueryEndpoints.getAllAndroidGamesEndpoint()

                }
                else -> applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint()
            }

            GenericJsonRequest(context, object : JsonRequestResponses {

                override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                    super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                    storefrontLiveData.processAllContentWordpress(rawDataJsonArray)

                    if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
                        Log.d(this@AllContent.javaClass.simpleName, "There Might Be More Data To Retrieve")

                        numberOfPageToRetrieve++

                        retrieveAllContentMoreWordpress()

                    } else {

                        allLoadingFinished = true

                    }

                }

            }).getMethod(endpoint)

        }

    }

    private fun retrieveAllContentMoreWordpress() {

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                storefrontLiveData.processAllContentMoreWordpress(rawDataJsonArray)

                if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
                    Log.d(this@AllContent.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    numberOfPageToRetrieve++

                    retrieveAllContentMoreWordpress()

                } else {

                    allLoadingFinished = true

                }

            }

            override fun jsonRequestResponseFailureHandler(jsonError: String?) {
                super.jsonRequestResponseFailureHandler(jsonError)

            }

            override fun jsonRequestResponseFailureHandler(networkError: Int?) {
                super.jsonRequestResponseFailureHandler(networkError)

            }

        }).getMethod( when (queryType) {
            GeneralEndpoints.QueryType.ApplicationsQuery -> {

                applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint(numberOfPage = numberOfPageToRetrieve)

            }
            GeneralEndpoints.QueryType.GamesQuery -> {

                gamesQueryEndpoints.getAllAndroidGamesEndpoint(numberOfPage = numberOfPageToRetrieve)

            }
            else -> applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint(numberOfPage = numberOfPageToRetrieve)
        })

    }

}