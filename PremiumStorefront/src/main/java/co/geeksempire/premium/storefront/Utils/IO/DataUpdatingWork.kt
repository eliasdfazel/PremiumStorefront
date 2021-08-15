/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/15/21, 12:11 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.IO

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.geeksempire.premium.storefront.Database.Write.InputProcess
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import org.json.JSONArray

class DataUpdatingWork(val appContext: Context, val workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    private val generalEndpoint = GeneralEndpoints()

    private val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoint)
    private val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoint)

    private val inputProcess: InputProcess by lazy {
        InputProcess(applicationContext)
    }

    var stringBuilder = StringBuilder()

    private var numberOfPageToRetrieve: Int = 1

    override suspend fun doWork(): Result {

        val updateDataKey = workerParams.inputData.getByteArray(IO.UpdateDataKey)?.let { String(it) }
        Log.d(this@DataUpdatingWork.javaClass.simpleName, updateDataKey.toString())

        /* Start - Applications Data Updating */
        when (updateDataKey) {
            IO.UpdateApplicationsDataKey -> {

                startApplicationsContentRetrieval(IO.UpdateApplicationsDataKey)

            }
            IO.UpdateGamesDataKey -> {

                startGamesContentRetrieval(IO.UpdateGamesDataKey)

            }
            IO.UpdateBooksDataKey -> {



            }
            IO.UpdateMoviesDataKey -> {

                startMoviesContentRetrieval(IO.UpdateMoviesDataKey)

            }
        }

        /* End - Applications Data Updating */

        delay(1357)

        return Result.success()
    }

    private fun startApplicationsContentRetrieval(updateDataKey: String) {

        GenericJsonRequest(applicationContext, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    stringBuilder.append(rawDataJsonArray.toString())

                    numberOfPageToRetrieve++

                    startApplicationsContentRetrieval(updateDataKey)

                } else {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "No More Content")

                    stringBuilder.append(rawDataJsonArray.toString())

                    inputProcess.writeDataToFile(updateDataKey, stringBuilder.toString())

                }

            }

        }).getMethod(applicationsQueryEndpoints.getAllAndroidApplicationsEndpoint(productPerPage = 99, numberOfPage = numberOfPageToRetrieve))

    }

    private fun startGamesContentRetrieval(updateDataKey: String) {

        GenericJsonRequest(applicationContext, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                if (rawDataJsonArray.length() == applicationsQueryEndpoints.defaultProductsPerPage) {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "There Might Be More Data To Retrieve")

                    stringBuilder.append(rawDataJsonArray.toString())

                    numberOfPageToRetrieve++

                    startApplicationsContentRetrieval(updateDataKey)

                } else {
                    Log.d(this@DataUpdatingWork.javaClass.simpleName, "No More Content")

                    stringBuilder.append(rawDataJsonArray.toString())

                    inputProcess.writeDataToFile(updateDataKey, stringBuilder.toString())

                }

            }

        }).getMethod(gamesQueryEndpoints.getAllAndroidGamesEndpoint(productPerPage = 99, numberOfPage = numberOfPageToRetrieve))

    }

    object GenreDataKey {
        const val GenreId = "genreId"
        const val GenreName = "genreName"
        const val GenreIconLink = "genreIconLink"
        const val ProductCount = "productCount"
    }

    @Keep
    data class GenreIds(var GenreIds: ArrayList<HashMap<String, Any>>? = null)

    @Keep
    data class StorefrontGenresData(var genreId: Int, var genreName: String, var genreIconLink: String, var productCount: Int, var selectedCategory: Boolean = false)

    private fun startMoviesContentRetrieval(updateDataKey: String) {

        Firebase.firestore
            .document("/PremiumStorefront/Products" + "/" + "Multimedia" + "/" + "Movies")
            .get(Source.SERVER).addOnSuccessListener { documentSnapshot ->

                if (documentSnapshot.exists()) {

                    val moviesDocumentSnapshots = ArrayList<StorefrontGenresData>()

                    documentSnapshot.toObject(GenreIds::class.java)!!.GenreIds?.forEach { documentMap ->

                        moviesDocumentSnapshots.add(StorefrontGenresData(
                            genreId = documentMap[GenreDataKey.GenreId].toString().toInt(),
                            genreName = documentMap[GenreDataKey.GenreName].toString(),
                            genreIconLink = documentMap[GenreDataKey.GenreIconLink].toString(),
                            productCount = documentMap[GenreDataKey.ProductCount].toString().toInt()
                        ))

                    }

                    val rawQuerySnapshot = ArrayList<QuerySnapshot>()

                    val allGenreCount = moviesDocumentSnapshots.size

                    var genreCounter = 0

                    moviesDocumentSnapshots.forEachIndexed { index, storefrontGenresData ->

                        Firebase.firestore
                            .collection("/PremiumStorefront/Products" + "/" + "Multimedia" + "/" + "Movies" + "/" + storefrontGenresData.genreName)
                            .get(Source.SERVER).addOnSuccessListener { querySnapshot ->

                                genreCounter++

                                if (!querySnapshot.isEmpty) {

                                    rawQuerySnapshot.add(querySnapshot)

                                }

                                if (allGenreCount == genreCounter) {



                                }

                            }

                    }

                }

            }.addOnFailureListener {
                it.printStackTrace()

            }

    }

}