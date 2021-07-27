/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 11:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.DataStructure

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class FavoriteProductsLiveData : ViewModel() {

    val favoritedContentItemData: MutableLiveData<ArrayList<FavoriteDataStructure>> by lazy {
        MutableLiveData<ArrayList<FavoriteDataStructure>>()
    }

    fun prepareFavoritedProductsData(querySnapshot: QuerySnapshot) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val favoritedContentItems: ArrayList<FavoriteDataStructure> = ArrayList<FavoriteDataStructure>()

        querySnapshot.documents.asFlow()
            .filter {

                it.exists()
            }
            .map {

                it
            }
            .collect {

                favoritedContentItems.add(FavoriteDataStructure(productId = it.getString(Favorite.ProductId)?:"net.geekstools.floatshort.PRO",
                    productName = it.getString(Favorite.ProductName)?:"Float It",
                    productDescription = it.getString(Favorite.ProductDescription)?:"Float It To Become Master Of Multitasking ⚡",
                    productIcon = it.getString(Favorite.ProductIcon)?:"https://geeksempire.co/wp-content/uploads/2021/04/Float-It.jpg",
                    productFavorited = it.getBoolean(Favorite.ProductFavorited)?:true
                ))

            }

        favoritedContentItemData.postValue(favoritedContentItems)

    }

}