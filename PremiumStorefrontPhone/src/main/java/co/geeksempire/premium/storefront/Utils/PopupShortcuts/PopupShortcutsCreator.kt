/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 6:43 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.PopupShortcuts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkConnections.GeneralEndpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.GenericJsonRequest
import co.geeksempire.premium.storefront.Utils.NetworkConnections.Requests.JsonRequestResponses
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger
import org.json.JSONArray
import org.json.JSONObject

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

/**
 * POPUP_SHORTCUTS_OPEN_PLAY_STORE
 **/
class PopupShortcutsCreator (val context: Context) {

    private val shortcutManager: ShortcutManager = context.getSystemService(ShortcutManager::class.java) as ShortcutManager

    private val categoryId: Long = 836

    fun configure() = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val generalEndpoint = GeneralEndpoint()

        GenericJsonRequest(context, object : JsonRequestResponses {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonArray: JSONArray) {
                super.jsonRequestResponseSuccessHandler(rawDataJsonArray)

                shortcutManager.removeAllDynamicShortcuts()

                for (indexContent in 0 until rawDataJsonArray.length()) {

                    val featuredContentJsonObject: JSONObject = rawDataJsonArray[indexContent] as JSONObject

                    /* Start - Images */
                    val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

                    val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
                    /* End - Images */

                    /* Start - Attributes */
                    val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

                    val attributesMap = HashMap<String, String>()

                    for (indexAttribute in 0 until featuredContentAttributes.length()) {

                        val attributesJsonObject: JSONObject =
                            featuredContentAttributes[indexAttribute] as JSONObject

                        attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] =
                            attributesJsonObject.getJSONArray(
                                ProductsContentKey.AttributeOptionsKey
                            )[0].toString()

                    }
                    /* End - Attributes */

                    addShortcutKeyboardTyping(PopupShortcutsData(
                        applicationPackageName = attributesMap[ProductsContentKey.AttributesPackageNameKey].toString(),
                        applicationName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                        applicationSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                        applicationIconLink = productIcon
                    ))

                }

                if (rawDataJsonArray.length() == generalEndpoint.defaultProductsPerPage) {



                } else {



                }

            }

        }).getMethod(generalEndpoint.getProductsSpecificCategoriesEndpoint(productCategoryId = categoryId))

    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun addShortcutKeyboardTyping(popupShortcutsData: PopupShortcutsData) {

        val shortcutsHomeLauncherCategories: HashSet<String> = HashSet<String>()
        shortcutsHomeLauncherCategories.addAll(arrayOf("Premium", "Storefront", "Shop", "Application", "Game", "Movie", "Book", "Music"))

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
            .transform(RoundedCorners(dpToInteger(context, 37)))
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()

                    return true
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

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