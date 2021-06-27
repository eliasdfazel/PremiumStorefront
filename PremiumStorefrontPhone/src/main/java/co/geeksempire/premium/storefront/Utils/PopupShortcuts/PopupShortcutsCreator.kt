/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/27/21, 11:01 AM
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
import androidx.annotation.RequiresApi
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.DataStructure.StorefrontContentsData
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

object PopupShortcutsItems {
    const val ShortcutId = "ShortcutId"
    const val ShortcutLabel = "ShortcutLabel"
    const val ShortcutDescription = "ShortcutDescription"
}

object PopupShortcutsActions {
    const val OpenPlayStoreAction = "POPUP_SHORTCUTS_OPEN_PLAY_STORE"
}

data class PopupShortcutsData(var applicationPackageName: String,
                              var applicationName: String, var applicationSummary: String, var applicationIconLink: String)

/**
 * POPUP_SHORTCUTS_OPEN_PLAY_STORE
 **/
class PopupShortcutsCreator (val context: Context) {

    private val shortcutManager: ShortcutManager = context.getSystemService(ShortcutManager::class.java) as ShortcutManager

    private val shortcutsDataList = ArrayList<StorefrontContentsData>(5)

    private var shortcutSetupCounter = 0

    fun configure(initialShortcutsDataList: List<StorefrontContentsData>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        shortcutsDataList.clear()
        shortcutsDataList.addAll(initialShortcutsDataList)

        if (initialShortcutsDataList.isNotEmpty()) {

            shortcutManager.removeAllDynamicShortcuts()

            addShortcutKeyboardTyping(
                PopupShortcutsData(
                    applicationPackageName = shortcutsDataList[shortcutSetupCounter].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString(),
                    applicationName = shortcutsDataList[shortcutSetupCounter].productName,
                    applicationSummary = shortcutsDataList[shortcutSetupCounter].productSummary,
                    applicationIconLink = shortcutsDataList[shortcutSetupCounter].productIconLink
                )
            )

        }

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

                        try {

                            shortcutSetupCounter++

                            addShortcutKeyboardTyping(PopupShortcutsData(
                                applicationPackageName = shortcutsDataList[shortcutSetupCounter].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString(),
                                applicationName = shortcutsDataList[shortcutSetupCounter].productName,
                                applicationSummary = shortcutsDataList[shortcutSetupCounter].productSummary,
                                applicationIconLink = shortcutsDataList[shortcutSetupCounter].productIconLink
                            ))

                        } catch (e: IndexOutOfBoundsException) {
                            e.printStackTrace()
                        }

                    }

                    return true
                }

            })
            .submit()

    }

}