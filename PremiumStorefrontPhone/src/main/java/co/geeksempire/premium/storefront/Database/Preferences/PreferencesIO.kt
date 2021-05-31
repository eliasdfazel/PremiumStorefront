/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/31/21, 12:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesIO (private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Preferences")

    suspend fun savePreferences(preferenceKey: Preferences.Key<String>, inputValue: String) {

        context.dataStore.edit { settings ->

            settings[preferenceKey] = inputValue

        }
    }

    suspend fun savePreferences(preferenceKey: Preferences.Key<Boolean>, inputValue: Boolean) {

        context.dataStore.edit { settings ->

            settings[preferenceKey] = inputValue

        }
    }

    fun readPreferencesString(preferenceKey: Preferences.Key<String>) : Flow<String?> {

        return context.dataStore.data.map { preferences -> preferences[preferenceKey] }
    }

    fun readPreferencesBoolean(preferenceKey: Preferences.Key<Boolean>) : Flow<Boolean?> {

        return context.dataStore.data.map { preferences -> preferences[preferenceKey] }
    }

}