/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/22/21, 10:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.SignInInterface
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Gesture.GestureListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener

abstract class StorefrontActivity : AppCompatActivity(),
    NetworkConnectionListenerInterface,
    SignInInterface,
    FragmentInterface,
    FirebaseInAppMessagingClickListener,
    GestureListenerInterface {}