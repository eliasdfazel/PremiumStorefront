/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 11:02 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Preferences.Theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import co.geeksempire.premium.storefront.Database.Preferences.PreferencesIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

object ThemeType {
    const val ThemeLight = true
    const val ThemeDark = false
}

class ThemePreferences (context: Context) {

    private val preferencesIO: PreferencesIO = PreferencesIO(context)

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