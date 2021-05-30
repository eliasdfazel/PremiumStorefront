/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/30/21, 11:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront

import android.app.Application
import android.os.Bundle
import co.geeksempire.premium.storefront.Database.GeneralConfigurations.FirestoreConfiguration
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore

class PremiumStorefrontApplication : Application() {

    val firestoreConfiguration: FirestoreConfiguration by lazy {
        FirestoreConfiguration()
    }

    lateinit var firestoreDatabase: FirebaseFirestore

    val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)

        firebaseAnalytics.logEvent(this@PremiumStorefrontApplication.javaClass.simpleName, Bundle().apply { putString(this@PremiumStorefrontApplication.javaClass.simpleName, "Started") })

        firestoreDatabase = firestoreConfiguration.initialize()

    }

}