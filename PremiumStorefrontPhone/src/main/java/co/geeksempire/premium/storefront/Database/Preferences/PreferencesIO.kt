/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/31/21, 12:37 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferencesKey {
    val ApplicationTheme = booleanPreferencesKey("ApplicationTheme")
}

class PreferencesIO (private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Preferences")

    suspend fun savePreferences(inputValue: Boolean) {

        context.dataStore.edit { settings ->

            settings[PreferencesKey.ApplicationTheme] = inputValue

        }
    }

    fun readPreferences() : Flow<Boolean?> {

        return context.dataStore.data.map { preferences -> preferences[PreferencesKey.ApplicationTheme] }
    }

}