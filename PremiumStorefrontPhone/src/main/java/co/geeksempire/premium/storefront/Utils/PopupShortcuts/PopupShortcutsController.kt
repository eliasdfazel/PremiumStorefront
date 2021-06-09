/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 7:07 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.PopupShortcuts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall

class PopupShortcutsController : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {

            openPlayStoreToInstall(
                context = applicationContext,
                aPackageName = it.getStringExtra(PopupShortcutsItems.ShortcutId)!!,
                applicationName = it.getStringExtra(PopupShortcutsItems.ShortcutLabel)!!,
                applicationSummary = it.getStringExtra(PopupShortcutsItems.ShortcutDescription)!!
            )

        }

        this@PopupShortcutsController.finish()

    }

}