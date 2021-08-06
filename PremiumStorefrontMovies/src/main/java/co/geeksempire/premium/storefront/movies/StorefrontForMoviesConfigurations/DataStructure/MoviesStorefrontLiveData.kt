/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/6/21, 11:16 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject

class MoviesStorefrontLiveData : ViewModel() {

    val featuredContentItemData: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val newContentItemData: MutableLiveData<ArrayList<StorefrontMoviesContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontMoviesContentsData>>()
    }

    val genresMoviesItemData: MutableLiveData<ArrayList<StorefrontGenresData>> by lazy {
        MutableLiveData<ArrayList<StorefrontGenresData>>()
    }

    fun processNewContent(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@MoviesStorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontMoviesContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            /* End - Images */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey).toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(
                StorefrontMoviesContentsData(
                    movieName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                    movieDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                    movieSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                    moviePosterLink = productIcon,
                    productAttributes = attributesMap
            ))

            Log.d(this@MoviesStorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(
                ProductsContentKey.NameKey)}")
        }

        newContentItemData.postValue(storefrontAllContents)

    }

    fun processGenreData(documentSnapshot: DocumentSnapshot) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val moviesDocumentSnapshots = ArrayList<StorefrontGenresData>()

        documentSnapshot.toObject(GenreIds::class.java)!!.GenreIds?.forEach { documentMap ->

            moviesDocumentSnapshots.add(StorefrontGenresData(
                genreId = documentMap[GenreDataKey.GenreId].toString().toInt(),
                genreName = documentMap[GenreDataKey.GenreName].toString(),
                genreIconLink = documentMap[GenreDataKey.GenreIconLink].toString(),
                productCount = documentMap[GenreDataKey.ProductCount].toString().toInt()
            ))

        }

        val moviesDocumentSnapshotsSorted = moviesDocumentSnapshots.sortedByDescending {

            it.productCount
        }

        moviesDocumentSnapshots.clear()
        moviesDocumentSnapshots.addAll(moviesDocumentSnapshotsSorted)

        genresMoviesItemData.postValue(moviesDocumentSnapshots)

    }

}