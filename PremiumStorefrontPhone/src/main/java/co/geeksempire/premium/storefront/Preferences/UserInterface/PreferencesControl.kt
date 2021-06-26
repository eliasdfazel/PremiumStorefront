/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/26/21, 6:48 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Preferences.DataStructure.PreferencesLiveData
import co.geeksempire.premium.storefront.Preferences.Extensions.preferencesControlSetupUserInteractions
import co.geeksempire.premium.storefront.Preferences.Extensions.preferencesControlSetupUserInterface
import co.geeksempire.premium.storefront.Preferences.Extensions.toggleLightDark
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.InApplicationReview.ReviewUtils
import co.geeksempire.premium.storefront.databinding.PreferencesControlLayoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PreferencesControl : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@PreferencesControl)
    }

    val preferencesLiveData: PreferencesLiveData by lazy {
        ViewModelProvider(this@PreferencesControl).get(PreferencesLiveData::class.java)
    }

    val reviewUtils: ReviewUtils by lazy {
        ReviewUtils((application as PremiumStorefrontApplication).preferencesIO)
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

            preferencesLiveData.toggleTheme.observe(this@PreferencesControl, Observer { themeType ->

                toggleLightDark()

            })

        }

    }

    override fun onBackPressed() {

        this@PreferencesControl.finish()
        overridePendingTransition(0, R.anim.slide_out_right)

    }

}