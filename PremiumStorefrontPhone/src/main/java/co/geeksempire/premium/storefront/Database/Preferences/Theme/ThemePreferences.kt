/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/31/21, 12:38 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Preferences.Theme

import android.content.Context
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
    fun checkThemeLightDark() : Flow<Boolean?> {

        return preferencesIO.readPreferences()
    }

    /**
     * Light = True - Dark = False
     **/
    fun changeLightDarkTheme(themeValue: Boolean) = CoroutineScope(Dispatchers.IO).async {

        preferencesIO.savePreferences(themeValue)
    }

}