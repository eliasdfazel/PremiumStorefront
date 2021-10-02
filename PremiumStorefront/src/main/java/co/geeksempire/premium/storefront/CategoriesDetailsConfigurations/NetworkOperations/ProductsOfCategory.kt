/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/2/21, 10:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.NetworkOperations

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.ProductsOfCategoryAdapter
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.Adapter.UniqueRecommendationsCategoryAdapter
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductDataStructure
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.*
import net.geeksempire.loadingspin.SpinKitView

class ProductsOfCategory : ViewModel() {

    val uniqueRecommendationsData: MutableLiveData<Pair<Boolean, StorefrontContentsData>> by lazy {
        MutableLiveData<Pair<Boolean, StorefrontContentsData>>()
    }

    val theCategoryProducts: MutableLiveData<DocumentSnapshot> by lazy {
        MutableLiveData<DocumentSnapshot>()
    }

    val uniqueRecommendationsProducts: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val generalEndpoints = GeneralEndpoints()

    private var numberOfPageToRetrieve: Int = 1

    var allLoadingFinished: Boolean = false

    fun retrieveProductsOfCategory(context: AppCompatActivity,
                                   categoryId: Int, categoryName: String,
                                   productsOfCategoryAdapter: ProductsOfCategoryAdapter,
                                   uniqueRecommendationsCategoryAdapter: UniqueRecommendationsCategoryAdapter,
                                   loadingView: SpinKitView,
                                   queryType: String = GeneralEndpoints.QueryType.ApplicationsQuery) {

        val applicationsQueryEndpoints: ApplicationsQueryEndpoints = ApplicationsQueryEndpoints(generalEndpoints)

        val gamesQueryEndpoints: GamesQueryEndpoints = GamesQueryEndpoints(generalEndpoints)

        val queryEndpoint = when (queryType) {
            GeneralEndpoints.QueryType.ApplicationsQuery -> {

                applicationsQueryEndpoints.firestoreApplicationsSpecificCategory(categoryName)

            }
            GeneralEndpoints.QueryType.GamesQuery -> {

                gamesQueryEndpoints.firestoreGamesSpecificCategory(categoryName)

            }
            else -> applicationsQueryEndpoints.firestoreApplicationsSpecificCategory(categoryName)
        }

        (context.application as PremiumStorefrontApplication)
            .firestoreDatabase
            .collection(queryEndpoint)
            .get(Source.SERVER).addOnSuccessListener { querySnapshot ->

                processAllProductsOfCategory(productsOfCategoryAdapter, uniqueRecommendationsCategoryAdapter, loadingView, querySnapshot)

            }.addOnFailureListener {



            }

    }


    fun processAllProductsOfCategory(productsOfCategoryAdapter: ProductsOfCategoryAdapter, uniqueRecommendationsCategoryAdapter: UniqueRecommendationsCategoryAdapter,
                                     loadingView: SpinKitView, querySnapshot: QuerySnapshot) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        querySnapshot.documents.forEachIndexed { index, documentSnapshot ->

            if (documentSnapshot.exists()) {

                documentSnapshot.data?.let {

                    val productDataStructure = ProductDataStructure(it)

                    if (productDataStructure.uniqueRecommendation()) {

                        uniqueRecommendationsCategoryAdapter.storefrontContents.add(documentSnapshot)

                        uniqueRecommendationsProducts.postValue(true)

                    } else {


                    }

                    productsOfCategoryAdapter.storefrontContents.add(documentSnapshot)


                    withContext(Dispatchers.Main) {

                        productsOfCategoryAdapter.notifyItemInserted(productsOfCategoryAdapter.itemCount)

                        uniqueRecommendationsCategoryAdapter.notifyItemInserted(uniqueRecommendationsCategoryAdapter.itemCount)

                    }

                    delay(197)

                }

            }

        }

        withContext(Dispatchers.Main) {

            loadingView.visibility = View.GONE

        }

    }

}