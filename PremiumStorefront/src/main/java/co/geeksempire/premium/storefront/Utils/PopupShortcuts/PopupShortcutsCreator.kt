/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/19/21, 9:45 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.PopupShortcuts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.*
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsIds
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsSerialize
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.ApplicationsQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GamesQueryEndpoints
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

object PopupShortcutsItems {
    const val ShortcutId = "ShortcutId"
    const val ShortcutLabel = "ShortcutLabel"
    const val ShortcutDescription = "ShortcutDescription"
}

object PopupShortcutsActions {
    const val OpenPlayStoreAction = "POPUP_SHORTCUTS_OPEN_PLAY_STORE"
}

@Keep
data class PopupShortcutsData(var applicationPackageName: String,
                              var applicationName: String, var applicationSummary: String, var applicationIconLink: String)

object QuickAccessKeys {
    const val ProductType = "ProductType"
    const val ProductCategory = "ProductCategory"
    const val ProductId = "ProductId"
}

/**
 * POPUP_SHORTCUTS_OPEN_PLAY_STORE
 **/
class PopupShortcutsCreator (val context: Context) {

    private val shortcutManager: ShortcutManager = context.getSystemService(ShortcutManager::class.java) as ShortcutManager

    fun startConfiguration() {

        val generalEndpoint = GeneralEndpoints()

        val applicationQueryEndpoint = ApplicationsQueryEndpoints(generalEndpoint)
        val gamesQueryEndpoint = GamesQueryEndpoints(generalEndpoint)

        val firestoreDatabase = Firebase.firestore

        firestoreDatabase.document("PremiumStorefront/Products/Android/QuickAccess")
            .get(Source.SERVER).addOnSuccessListener { documentSnapshot ->

                if (documentSnapshot.exists()) {

                    shortcutManager.removeAllDynamicShortcuts()

                    documentSnapshot.toObject(ProductsIds::class.java)!!.ProductsIds?.forEach {

                        val productCategory = it[QuickAccessKeys.ProductCategory].toString()
                        val productId = it[QuickAccessKeys.ProductId].toString()

                        val productDocumentEndpoint = when (it[QuickAccessKeys.ProductType]) {
                            "Applications" -> {

                                applicationQueryEndpoint.firestoreSpecificApplication(productCategory, productId)

                            }
                            "Games" -> {

                                gamesQueryEndpoint.firestoreSpecificGame(productCategory, productId)

                            } else -> applicationQueryEndpoint.firestoreSpecificApplication(productCategory, productId)
                        }

                        firestoreDatabase.document(productDocumentEndpoint)
                            .get(Source.SERVER).addOnSuccessListener { productDocument ->

                                if (productDocument.exists()) {
                                    Log.d(this@PopupShortcutsCreator.javaClass.simpleName, productDocument[StorefrontContentsSerialize.ProductName].toString())

                                    addShortcutKeyboardTyping(PopupShortcutsData(
                                        applicationPackageName = productDocument[StorefrontContentsSerialize.PackageName].toString(),
                                        applicationName = productDocument[StorefrontContentsSerialize.ProductName].toString(),
                                        applicationSummary = productDocument[StorefrontContentsSerialize.ProductSummary].toString(),
                                        applicationIconLink = productDocument[StorefrontContentsSerialize.ProductIconLink].toString()
                                    ))

                                }

                            }

                    }

                }

            }.addOnFailureListener {



            }

    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun addShortcutKeyboardTyping(popupShortcutsData: PopupShortcutsData) {

        val shortcutsHomeLauncherCategories: HashSet<String> = HashSet<String>()
        shortcutsHomeLauncherCategories.addAll(arrayOf("Premium", "Storefront", "Shopping", "Application", "Game", "Movie", "Book"))

        val intent = Intent(context, PopupShortcutsController::class.java).apply {
            action = PopupShortcutsActions.OpenPlayStoreAction
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        intent.putExtra(PopupShortcutsItems.ShortcutId, popupShortcutsData.applicationPackageName)
        intent.putExtra(PopupShortcutsItems.ShortcutLabel, popupShortcutsData.applicationName)
        intent.putExtra(PopupShortcutsItems.ShortcutDescription, popupShortcutsData.applicationSummary)

        Glide.with(context)
            .asBitmap()
            .load(popupShortcutsData.applicationIconLink)
            .transform(RoundedCorners(dpToInteger(context, 51)))
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()

                    return true
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        shortcutManager.addDynamicShortcuts(arrayListOf(
                            ShortcutInfo.Builder(context, popupShortcutsData.applicationPackageName)
                                .setShortLabel(popupShortcutsData.applicationName)
                                .setLongLabel(popupShortcutsData.applicationName)
                                .setIcon(Icon.createWithBitmap(resource))
                                .setIntent(intent)
                                .setCategories(shortcutsHomeLauncherCategories)
                                .build()
                        ))

                    }

                    return true
                }

            })
            .submit()

    }

}