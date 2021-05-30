/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/30/21, 11:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.GeneralConfigurations

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class FirestoreConfiguration {

    private val firebaseFirestore = Firebase.firestore

    fun initialize() : FirebaseFirestore {

        val firebaseFirestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
            cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }

        firebaseFirestore.firestoreSettings = firebaseFirestoreSettings

        return firebaseFirestore
    }

    fun forceReadCache() {

        firebaseFirestore.disableNetwork()

    }

    fun forceReadInternet() {

        firebaseFirestore.enableNetwork()

    }

}