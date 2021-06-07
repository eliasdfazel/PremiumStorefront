/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/7/21, 6:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudNotifications : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

}