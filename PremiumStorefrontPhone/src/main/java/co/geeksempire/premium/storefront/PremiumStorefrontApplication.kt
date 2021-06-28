/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 4:48 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront

import android.app.Application
import android.os.Bundle
import co.geeksempire.premium.storefront.Database.GeneralConfigurations.FirestoreConfiguration
import co.geeksempire.premium.storefront.Database.Preferences.PreferencesIO
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.CategoryContent.CategoryData
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore

class PremiumStorefrontApplication : Application() {

    val preferencesIO: PreferencesIO by lazy {
        PreferencesIO(context = applicationContext)
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

        firebaseAnalytics.logEvent(this@PremiumStorefrontApplication.javaClass.simpleName, Bundle().apply { putString(this@PremiumStorefrontApplication.javaClass.simpleName, "Started") })

        firestoreDatabase = firestoreConfiguration.initialize()

    }

}