/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/20/21, 8:51 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Notifications

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

interface SubscriptionInterface {
    fun subscriptionSucceed(){}
    fun subscriptionFailed(){}
}

class RemoteSubscriptions {

    fun subscribe(topicToSubscribe: String, subscriptionInterface: SubscriptionInterface?) {

        Firebase.messaging.subscribeToTopic(topicToSubscribe).addOnSuccessListener {

            subscriptionInterface?.subscriptionSucceed()

        }.addOnFailureListener {

            subscriptionInterface?.subscriptionFailed()

        }

    }

}