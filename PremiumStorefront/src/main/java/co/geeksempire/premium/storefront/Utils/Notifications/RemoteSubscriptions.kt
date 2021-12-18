/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/18/21, 5:13 AM
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