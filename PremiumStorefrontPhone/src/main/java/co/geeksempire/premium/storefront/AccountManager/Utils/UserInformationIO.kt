/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 6:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.Utils

import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.Database.Preferences.PreferencesIO
import kotlinx.coroutines.flow.Flow

class UserInformationIO(private val context: AppCompatActivity) {

    private val preferencesIO = PreferencesIO(context = context, preferenceDatabaseName = "UserProfileInformation", coroutineScope = context.lifecycleScope)

    suspend fun saveUserInformation(userEmailAddress: String) {

        preferencesIO.savePreferences(stringPreferencesKey("Email"), userEmailAddress)

    }

    suspend fun getUserAccountName() : Flow<String?> {

        return preferencesIO.readPreferencesString(stringPreferencesKey("Email"))
    }

    suspend fun savePrivacyAgreement() {

        preferencesIO.savePreferences(booleanPreferencesKey("PrivacyAgreement"), true)

    }

    fun readPrivacyAgreement() : Flow<Boolean> {

        return preferencesIO.readPreferencesBoolean(booleanPreferencesKey("PrivacyAgreement"), false)
    }

}