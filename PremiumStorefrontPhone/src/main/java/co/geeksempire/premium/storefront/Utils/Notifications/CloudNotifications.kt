/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/25/21, 5:32 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.Notifications

import co.geeksempire.premium.storefront.Utils.IO.IO
import co.geeksempire.premium.storefront.Utils.IO.UpdatingDataIO
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudNotifications : FirebaseMessagingService() {

    private val updatingDataIO: UpdatingDataIO by lazy {
        UpdatingDataIO(applicationContext)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        remoteMessage.data.let { linkedHashMapData ->

            linkedHashMapData[IO.UpdateDataKey]?.let { updateDataKey ->
                when (updateDataKey) {
                    IO.UpdateApplicationsDataKey -> {

                        updatingDataIO.startUpdatingApplicationsData()

                    }
                    IO.UpdateGamesDataKey -> {

                        updatingDataIO.startUpdatingGamesData()

                    }
                    IO.UpdateBooksDataKey -> {

                        updatingDataIO.startUpdatingBooksData()

                    }
                    IO.UpdateMoviesDataKey -> {

                        updatingDataIO.startUpdatingMoviesData()

                    }
                }
            }

        }

    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

}