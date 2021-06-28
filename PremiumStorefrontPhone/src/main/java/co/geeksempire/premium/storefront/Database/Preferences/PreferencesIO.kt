/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 7:18 AM
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesIO (private val context: Context,
                     private val preferenceDatabaseName: String = "Preferences",
                     private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = preferenceDatabaseName, scope = coroutineScope)

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

    suspend fun savePreferences(preferenceKey: Preferences.Key<Int>, inputValue: Int) {

        context.dataStore.edit { settings ->

            settings[preferenceKey] = inputValue

        }
    }

    fun readPreferencesString(preferenceKey: Preferences.Key<String>, defaultValue: String) : Flow<String> {

        return context.dataStore.data.map { preferences -> preferences[preferenceKey]?:defaultValue }
    }

    fun readPreferencesBoolean(preferenceKey: Preferences.Key<Boolean>, defaultValue: Boolean) : Flow<Boolean> {

        return context.dataStore.data.map { preferences -> preferences[preferenceKey]?:defaultValue }
    }

}