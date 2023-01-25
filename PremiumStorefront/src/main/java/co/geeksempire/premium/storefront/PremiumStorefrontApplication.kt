/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/21/21, 10:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront

import android.content.Context
import android.os.Bundle
import co.geeksempire.premium.storefront.Database.GeneralConfigurations.FirestoreConfiguration
import co.geeksempire.premium.storefront.Database.Preferences.PreferencesIO
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontSections.CategoryContent.CategoryData
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.firestore.FirebaseFirestore

class PremiumStorefrontApplication : SplitCompatApplication() {

    val preferencesIO: PreferencesIO by lazy {
        PreferencesIO(context = applicationContext)
    }

    val entryPreferences: EntryPreferences by lazy {
        EntryPreferences(preferencesIO)
    }

    val firestoreConfiguration: FirestoreConfiguration by lazy {
        FirestoreConfiguration()
    }

    lateinit var firestoreDatabase: FirebaseFirestore

    val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(applicationContext)
    }

    val categoryData = CategoryData()

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)

        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )

        firebaseAnalytics.logEvent(this@PremiumStorefrontApplication.javaClass.simpleName, Bundle().apply { putString(this@PremiumStorefrontApplication.javaClass.simpleName, "Started") })

        firestoreDatabase = firestoreConfiguration.initialize()

    }

    override fun attachBaseContext(baseApplicationContext: Context) {
        super.attachBaseContext(baseApplicationContext)

        SplitCompat.install(this@PremiumStorefrontApplication)
    }

}