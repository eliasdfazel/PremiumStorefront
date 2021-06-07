/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/7/21, 12:44 PM
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
import co.geeksempire.premium.storefront.Invitations.Send.SendInvitation
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.databinding.AccountInformationLayoutBinding
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class AccountInformation : AppCompatActivity(), NetworkConnectionListenerInterface, SignInInterface {

    val userInformationIO: UserInformationIO by lazy {
        UserInformationIO(applicationContext)
    }

    val accountDataStructure = AccountDataStructure()

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@AccountInformation, accountInformationLayoutBinding.rootView, networkCheckpoint)
    }

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

        accountManagerSetupUserInterface()

        networkConnectionListener.networkConnectionListenerInterface = this@AccountInformation

        if (Firebase.auth.currentUser != null
            && Firebase.auth.currentUser!!.isAnonymous) {

            accountInformationLayoutBinding.signupLoadingView.visibility = View.VISIBLE
            accountInformationLayoutBinding.signupLoadingView.playAnimation()

            accountInformationLayoutBinding.root.post {

                accountSelector.launch(AccountSignIn.GoogleSignInRequestCode)

            }

        } else if (Firebase.auth.currentUser != null) {

            accountInformationLayoutBinding.socialMediaScrollView.visibility = View.VISIBLE
            accountInformationLayoutBinding.inviteFriendsView.visibility = View.VISIBLE

            (application as PremiumStorefrontApplication).firestoreDatabase
                .document(accountDataStructure.userProfileDatabasePath(Firebase.auth.currentUser!!.uid, Firebase.auth.currentUser!!.email))
                .get()
                .addOnSuccessListener { documentSnapshot ->

                    documentSnapshot?.let { documentData ->

                        accountInformationLayoutBinding.socialMediaScrollView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                        accountInformationLayoutBinding.socialMediaScrollView.visibility = View.VISIBLE

                        accountInformationLayoutBinding.instagramAddressView.setText(documentData.data?.get(
                            AccountDataStructure.Attributes.instagramAccount).toString().lowercase(Locale.getDefault()))

                        accountInformationLayoutBinding.twitterAddressView.setText(documentData.data?.get(
                            AccountDataStructure.Attributes.twitterAccount).toString())

                        accountInformationLayoutBinding.phoneNumberAddressView.setText(documentData.data?.get(
                            AccountDataStructure.Attributes.phoneNumber).toString())

                        accountInformationLayoutBinding.inviteFriendsView.setOnClickListener {

                            SendInvitation(this@AccountInformation, accountInformationLayoutBinding.root)
                                .invite(Firebase.auth.currentUser!!)

                        }

                    }

                }

        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {

        if (profileUpdating) {

            profileUpdating = false

            this@AccountInformation.finish()

        } else {

            this@AccountInformation.finish()

        }

        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_out_right)

    }

    override fun networkAvailable() {

    }

    override fun networkLost() {

    }

    override fun signInProcessSucceed(authenticationResult: AuthResult) {
        super.signInProcessSucceed(authenticationResult)

    }

    override fun signInProcessFailed(error: String?) {
        super.signInProcessFailed(error)

        this@AccountInformation.finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_out_right)

    }

}