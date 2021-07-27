/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 11:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.Utils

import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import kotlinx.coroutines.flow.Flow

class UserInformationIO(context: AppCompatActivity) {

    private val preferencesIO = (context.application as PremiumStorefrontApplication).preferencesIO

    suspend fun savePrivacyAgreement() {

        preferencesIO.savePreferences(booleanPreferencesKey("PrivacyAgreement"), true)

    }

    fun readPrivacyAgreement() : Flow<Boolean> {

        return preferencesIO.readPreferencesBoolean(booleanPreferencesKey("PrivacyAgreement"), false)
    }

}