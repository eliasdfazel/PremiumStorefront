/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/17/21, 6:09 AM
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
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONObject

class MoviesStorefrontLiveData : ViewModel() {

    val featuredContentItemData: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val newContentItemData: MutableLiveData<ArrayList<StorefrontMoviesContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontMoviesContentsData>>()
    }

    val allMoviesItemData: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val allMoviesOfGenre: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val allUniqueMoviesOfGenre: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val allFilteredMoviesItemData: MutableLiveData<Pair<ArrayList<DocumentSnapshot>, Boolean>> by lazy {
        MutableLiveData<Pair<ArrayList<DocumentSnapshot>, Boolean>>()
    }

    val genresMoviesItemData: MutableLiveData<ArrayList<StorefrontGenresData>> by lazy {
        MutableLiveData<ArrayList<StorefrontGenresData>>()
    }

    fun processNewContent(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@MoviesStorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontMoviesContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            val movieId = featuredContentJsonObject[ProductsContentKey.IdKey].toString()

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
                    movieId = JSONArray(attributesMap[MoviesDataKey.MovieId]).get(0).toString(),
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

    fun processAllMoviesGenreData(documentSnapshot: DocumentSnapshot) = flow<ArrayList<StorefrontGenresData>> {

        val moviesDocumentSnapshots = ArrayList<StorefrontGenresData>()

        documentSnapshot.toObject(GenreIds::class.java)!!.GenreIds?.forEach { documentMap ->

            moviesDocumentSnapshots.add(StorefrontGenresData(
                genreId = documentMap[GenreDataKey.GenreId].toString().toInt(),
                genreName = documentMap[GenreDataKey.GenreName].toString(),
                genreIconLink = documentMap[GenreDataKey.GenreIconLink].toString(),
                productCount = documentMap[GenreDataKey.ProductCount].toString().toInt()
            ))

        }

        emit(moviesDocumentSnapshots)

    }

    fun processAllMoviesGenreDataWithExclusion(documentSnapshot: DocumentSnapshot, excludedMovie: String) = flow<ArrayList<StorefrontGenresData>> {

        val moviesDocumentSnapshots = ArrayList<StorefrontGenresData>()

        documentSnapshot.toObject(GenreIds::class.java)!!.GenreIds?.forEach { documentMap ->

            if (documentMap[GenreDataKey.GenreName].toString() != excludedMovie) {

                moviesDocumentSnapshots.add(StorefrontGenresData(
                    genreId = documentMap[GenreDataKey.GenreId].toString().toInt(),
                    genreName = documentMap[GenreDataKey.GenreName].toString(),
                    genreIconLink = documentMap[GenreDataKey.GenreIconLink].toString(),
                    productCount = documentMap[GenreDataKey.ProductCount].toString().toInt()
                ))

            }

        }

        emit(moviesDocumentSnapshots)

    }

    fun processAllMoviesData(allQuerySnapshot: ArrayList<QuerySnapshot>) = CoroutineScope(Dispatchers.IO).launch {

        val allMovies = ArrayList<DocumentSnapshot>()

        allQuerySnapshot.forEachIndexed { index, querySnapshot ->

            querySnapshot.documents.forEach {

                allMovies.add(it)

            }

        }

        allMoviesItemData.postValue(allMovies)

    }

    fun processAllMoviesData(allQuerySnapshot: ArrayList<QuerySnapshot>, selectedGenre: String) = CoroutineScope(Dispatchers.IO).launch {

        val allMovies = ArrayList<DocumentSnapshot>()

        allQuerySnapshot.forEachIndexed { index, querySnapshot ->

            querySnapshot.documents.forEach {

                it.data?.let { moviesData ->

                    val moviesDataStructure = MoviesDataStructure(moviesData)

                    if (moviesDataStructure.movieGenres().contains(selectedGenre)) {

                        allMovies.add(it)

                    }

                }

            }

        }

        allMoviesItemData.postValue(allMovies)

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

    fun processAllMoviesOfGenre(querySnapshot: QuerySnapshot) {

        val uniqueMoviesOfGenre = ArrayList<DocumentSnapshot>()

        val moviesOfGenre = ArrayList<DocumentSnapshot>()

        querySnapshot.documents.forEachIndexed { index, documentSnapshot ->

            if (documentSnapshot.exists()) {

                documentSnapshot.data?.let {

                    val moviesDataStructure = MoviesDataStructure(it)

                    if (moviesDataStructure.movieUniqueRecommendation()) {

                        uniqueMoviesOfGenre.add(documentSnapshot)

                    } else {


                    }

                    moviesOfGenre.add(documentSnapshot)

                }

            }

        }

        allUniqueMoviesOfGenre.postValue(uniqueMoviesOfGenre)

        allMoviesOfGenre.postValue(moviesOfGenre)

    }

    fun processAllMoviesOfGenre(querySnapshot: QuerySnapshot, excludedMovie: String) {

        val moviesOfGenre = ArrayList<DocumentSnapshot>()

        querySnapshot.documents.forEachIndexed { index, documentSnapshot ->

            if (documentSnapshot.exists()) {

                documentSnapshot.data?.let {

                    val moviesDataStructure = MoviesDataStructure(it)

                    if (moviesDataStructure.movieProductId() != excludedMovie) {

                        moviesOfGenre.add(documentSnapshot)

                    }

                }

            }

        }

        allMoviesOfGenre.postValue(moviesOfGenre)

    }

}