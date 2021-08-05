/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/5/21, 11:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

class MoviesStorefrontLiveData : ViewModel() {

    val featuredContentItemData: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val genresMoviesItemData: MutableLiveData<ArrayList<StorefrontGenresData>> by lazy {
        MutableLiveData<ArrayList<StorefrontGenresData>>()
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