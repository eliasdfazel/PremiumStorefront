/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 11:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Preferences.Theme

import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

object ThemeType {
    const val ThemeLight = true
    const val ThemeDark = false
}

class ThemePreferences (context: AppCompatActivity) {

    private val preferencesIO = (context.application as PremiumStorefrontApplication).preferencesIO

    /**
     * Light = True - Dark = False
     **/
    fun checkThemeLightDark() : Flow<Boolean> {

        return preferencesIO.readPreferencesBoolean(booleanPreferencesKey("ApplicationTheme"), ThemeType.ThemeLight)
    }

    /**
     * Light = True - Dark = False
     **/
    fun changeLightDarkTheme(themeValue: Boolean) = CoroutineScope(Dispatchers.IO).async {

        preferencesIO.savePreferences(booleanPreferencesKey("ApplicationTheme"),  themeValue)
    }

}