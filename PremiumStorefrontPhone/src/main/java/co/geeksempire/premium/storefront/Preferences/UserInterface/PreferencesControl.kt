/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 9:17 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Preferences.DataStructure.PreferencesLiveData
import co.geeksempire.premium.storefront.Preferences.Extensions.preferencesControlSetupUserInterface
import co.geeksempire.premium.storefront.databinding.PreferencesControlLayoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PreferencesControl : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(applicationContext)
    }

    val preferencesLiveData: PreferencesLiveData by lazy {
        ViewModelProvider(this@PreferencesControl).get(PreferencesLiveData::class.java)
    }

    val firebaseUser = Firebase.auth.currentUser

    lateinit var preferencesControlLayoutBinding: PreferencesControlLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesControlLayoutBinding =  PreferencesControlLayoutBinding.inflate(layoutInflater)
        setContentView(preferencesControlLayoutBinding.root)

        preferencesControlLayoutBinding.root.post {

            preferencesControlSetupUserInterface()



        }

    }

}