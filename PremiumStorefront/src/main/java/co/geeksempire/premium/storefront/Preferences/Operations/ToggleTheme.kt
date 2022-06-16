/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/1/21, 6:46 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Operations

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.Preferences.UserInterface.PreferencesControl
import co.geeksempire.premium.storefront.databinding.PreferencesControlLayoutBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ToggleTheme (private val context: AppCompatActivity,
                   private val themePreferences: ThemePreferences,
                   private val preferencesControlViewBinding: PreferencesControlLayoutBinding) {

    fun initialThemeToggleAction() {

        context.lifecycle.coroutineScope.launch {

            when (themePreferences.checkThemeLightDark().first()) {
                ThemeType.ThemeLight -> {

                    preferencesControlViewBinding.themeToggleView.frame = 1

                }
                ThemeType.ThemeDark -> {

                    preferencesControlViewBinding.themeToggleView.frame = 251

                }
                else -> {}
            }


        }

        toggleLightDarkTheme()

    }

    private fun toggleLightDarkTheme() {

        preferencesControlViewBinding.themeToggleView.setOnClickListener { view ->

            preferencesControlViewBinding.themeToggleView.also {

                context.lifecycle.coroutineScope.launch {

                    when (themePreferences.checkThemeLightDark().first()) {
                        ThemeType.ThemeLight -> {

                            it.speed = 3f
                            it.setMinAndMaxFrame(1, 251)

                            if (!it.isAnimating) {
                                it.playAnimation()
                            }

                            themePreferences.changeLightDarkTheme(ThemeType.ThemeDark).await()

                            when(context) {
                                is PreferencesControl -> {

                                    (context as PreferencesControl).preferencesLiveData.toggleTheme.postValue(ThemeType.ThemeDark)

                                }
                            }

                        }
                        ThemeType.ThemeDark -> {

                            it.speed = -3f
                            it.setMinAndMaxFrame(1, 251)

                            if (!it.isAnimating) {
                                it.playAnimation()
                            }

                            themePreferences.changeLightDarkTheme(ThemeType.ThemeLight).await()

                            when(context) {
                                is PreferencesControl -> {

                                    (context as PreferencesControl).preferencesLiveData.toggleTheme.postValue(ThemeType.ThemeLight)

                                }
                            }

                        }
                        else -> {}
                    }

                }

            }

        }

        preferencesControlViewBinding.root.setOnClickListener {



        }

    }

}