package co.geeksempire.administratorpanel

import android.os.Bundle
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontActivity
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage

class AdministratorEntry : StorefrontActivity() {

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (networkCheckpoint.networkConnection()) {



        }

    }

    override fun networkAvailable() {


    }

    override fun networkLost() {


    }

    override fun messageClicked(inAppMessage: InAppMessage, action: Action) {



    }

}