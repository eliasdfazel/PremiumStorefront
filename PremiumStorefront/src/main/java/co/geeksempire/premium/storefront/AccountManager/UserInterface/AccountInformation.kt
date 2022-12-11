/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/21/21, 12:00 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.UserInterface

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.AccountManager.DataStructure.AccountDataStructure
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.AccountSignIn
import co.geeksempire.premium.storefront.AccountManager.SignInProcess.SignInInterface
import co.geeksempire.premium.storefront.AccountManager.UserInterface.Extensions.accountManagerSetupUserInterface
import co.geeksempire.premium.storefront.AccountManager.Utils.UserInformationIO
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.Utils.Operations.NavigationListener
import co.geeksempire.premium.storefront.Utils.Operations.NavigationOperations
import co.geeksempire.premium.storefront.databinding.AccountInformationLayoutBinding
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountInformation : AppCompatActivity(), NetworkConnectionListenerInterface, SignInInterface, NavigationListener {

    val userInformationIO: UserInformationIO by lazy {
        UserInformationIO(this@AccountInformation)
    }

    val accountDataStructure = AccountDataStructure()

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@AccountInformation, accountInformationLayoutBinding.rootView, networkCheckpoint)
    }

    var developerChecked: Boolean = false

    var profileUpdating: Boolean = false

    /* Start - Sign In */
    val accountSignIn: AccountSignIn by lazy {
        AccountSignIn(this@AccountInformation, this@AccountInformation)
    }

    val accountSelector: ActivityResultLauncher<Any?> = registerForActivityResult(accountSignIn.createProcess()) {



    }
    /* End - Sign In */

    lateinit var accountInformationLayoutBinding: AccountInformationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountInformationLayoutBinding = AccountInformationLayoutBinding.inflate(layoutInflater)
        setContentView(accountInformationLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@AccountInformation

        if (Firebase.auth.currentUser == null) {

            accountInformationLayoutBinding.signupLoadingView.visibility = View.VISIBLE
            accountInformationLayoutBinding.signupLoadingView.playAnimation()

            accountInformationLayoutBinding.root.post {

                accountSelector.launch(AccountSignIn.GoogleSignInRequestCode)

            }

        } else if (Firebase.auth.currentUser != null) {

            accountManagerSetupUserInterface()

        }

    }

    override fun onStart() {
        super.onStart()

        NavigationOperations(this@AccountInformation)
            .listenBackPressed(this@AccountInformation)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun backNavigation() {

        if (profileUpdating) {

            profileUpdating = false

            this@AccountInformation.finish()

        } else {

            this@AccountInformation.finish()

        }

        overridePendingTransition(0, R.anim.slide_out_right)

    }

    override fun networkAvailable() {

    }

    override fun networkLost() {

    }

    override fun signInProcessSucceed(authenticationResult: AuthResult) {
        super.signInProcessSucceed(authenticationResult)

        accountInformationLayoutBinding.signupLoadingView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
        accountInformationLayoutBinding.signupLoadingView.visibility = View.GONE

        accountManagerSetupUserInterface()

    }

    override fun signInProcessFailed(error: String?) {
        super.signInProcessFailed(error)

        this@AccountInformation.finish()
        overridePendingTransition(0, R.anim.slide_out_right)

    }

}