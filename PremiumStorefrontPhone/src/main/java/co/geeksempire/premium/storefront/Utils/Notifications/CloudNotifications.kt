/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 5:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Notifications

import co.geeksempire.premium.storefront.Utils.IO.IO
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudNotifications : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val linkedHashMapData = remoteMessage.data

        linkedHashMapData[IO.UpdateDataKey]?.let { updateDataKey ->
            when (updateDataKey) {
                IO.UpdateApplicationsDataKey -> {



                }
                IO.UpdateGamesDataKey -> {



                }
                IO.UpdateBooksDataKey -> {



                }
                IO.UpdateMoviesDataKey -> {



                }
            }
        }

    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

}