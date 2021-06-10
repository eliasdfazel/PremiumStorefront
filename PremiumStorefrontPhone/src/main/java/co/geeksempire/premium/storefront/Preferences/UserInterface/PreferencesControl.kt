/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/10/21, 7:05 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.UserInterface

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Preferences.DataStructure.PreferencesLiveData
import co.geeksempire.premium.storefront.Preferences.Extensions.preferencesControlSetupUserInteractions
import co.geeksempire.premium.storefront.Preferences.Extensions.preferencesControlSetupUserInterface
import co.geeksempire.premium.storefront.Preferences.Extensions.toggleLightDark
import co.geeksempire.premium.storefront.databinding.PreferencesControlLayoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PreferencesControl : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@PreferencesControl)
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

            preferencesControlSetupUserInteractions()

            preferencesLiveData.toggleTheme.observe(this@PreferencesControl, Observer {

                var delayTheme: Long = 3333

                lifecycle.coroutineScope.launch {

                    themePreferences.checkThemeLightDark().collect {

                        when (it) {
                            ThemeType.ThemeLight -> {
                                delayTheme = 3000
                            }
                            ThemeType.ThemeDark -> {
                                delayTheme = 1133
                            }
                        }

                        if (it) {

                            Handler(Looper.getMainLooper()).postDelayed({

                                toggleLightDark()

                            }, delayTheme)

                        } else {

                            toggleLightDark()

                        }

                    }

                }

            })

        }

    }

}