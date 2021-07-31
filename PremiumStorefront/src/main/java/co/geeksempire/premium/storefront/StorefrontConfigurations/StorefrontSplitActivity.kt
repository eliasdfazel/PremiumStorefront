/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/31/21, 7:26 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.SignInInterface
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Gesture.GestureListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener

abstract class StorefrontSplitActivity : AppCompatActivity(),
    NetworkConnectionListenerInterface,
    SignInInterface,
    FragmentInterface,
    FirebaseInAppMessagingClickListener,
    GestureListenerInterface {

    object MoviesModule {
        const val EntryConfigurations = "co.geeksempire.premium.storefront.movies.EntryConfigurationsMovies"
    }

    object BooksModule {
        const val EntryConfigurations = "co.geeksempire.premium.storefront.books.EntryConfigurationsBooks"
    }

    override fun attachBaseContext(baseContext: Context) {
        super.attachBaseContext(baseContext)
        SplitCompat.install(this@StorefrontSplitActivity)
    }

}