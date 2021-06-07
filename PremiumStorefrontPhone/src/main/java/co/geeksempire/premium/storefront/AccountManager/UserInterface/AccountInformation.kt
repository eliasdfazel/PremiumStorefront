/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/7/21, 8:12 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.UserInterface

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.AccountManager.DataStructure.AccountDataStructure
import co.geeksempire.premium.storefront.AccountManager.UserInterface.Extensions.accountManagerSetupUserInterface
import co.geeksempire.premium.storefront.AccountManager.Utils.UserInformationIO
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.premium.storefront.databinding.AccountInformationLayoutBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class AccountInformation : AppCompatActivity(), NetworkConnectionListenerInterface {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(applicationContext)
    }

    val userInformationIO: UserInformationIO by lazy {
        UserInformationIO(applicationContext)
    }

    val accountDataStructure = AccountDataStructure()

    var profileUpdating: Boolean = false

    lateinit var accountInformationLayoutBinding: AccountInformationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountInformationLayoutBinding = AccountInformationLayoutBinding.inflate(layoutInflater)
        setContentView(accountInformationLayoutBinding.root)

        accountManagerSetupUserInterface()

        (application as KeepNoteApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@AccountInformation, accountInformationLayoutBinding.rootView)
            .inject(this@AccountInformation)

        networkConnectionListener.networkConnectionListenerInterface = this@AccountInformation

        if (Firebase.auth.currentUser != null
            && Firebase.auth.currentUser!!.isAnonymous) {

            accountInformationLayoutBinding.signupLoadingView.visibility = View.VISIBLE
            accountInformationLayoutBinding.signupLoadingView.playAnimation()

            accountInformationLayoutBinding.root.post {

                Handler(Looper.getMainLooper()).postDelayed({
                    Log.d(this@AccountInformation.javaClass.simpleName, "User Is Anonymous. Start Identification Process")

                    userInformation.startSignInProcess()

                }, 531)

            }

        } else if (Firebase.auth.currentUser != null) {

            (application as KeepNoteApplication).firestoreDatabase
                .document(UserInformation.userProfileDatabasePath(Firebase.auth.currentUser!!.uid))
                .get()
                .addOnSuccessListener { documentSnapshot ->

                    documentSnapshot?.let { documentData ->

                        accountInformationLayoutBinding.socialMediaScrollView.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                        accountInformationLayoutBinding.socialMediaScrollView.visibility = View.VISIBLE

                        accountInformationLayoutBinding.instagramAddressView.setText(documentData.data?.get(
                            UserInformationDataStructure.instagramAccount).toString().toLowerCase(Locale.getDefault()))

                        accountInformationLayoutBinding.twitterAddressView.setText(documentData.data?.get(
                            UserInformationDataStructure.twitterAccount).toString())

                        accountInformationLayoutBinding.phoneNumberAddressView.setText(documentData.data?.get(
                            UserInformationDataStructure.phoneNumber).toString())

                        accountInformationLayoutBinding.inviteFriendsView.visibility = View.VISIBLE
                        accountInformationLayoutBinding.inviteFriendsView.setOnClickListener {

                            if (checkSelfPermission(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {

                                SendInvitation(this@AccountInformation, accountInformationLayoutBinding.root)
                                    .invite(Firebase.auth.currentUser!!)

                            } else {

                                val permissionsList = arrayListOf(
                                    Manifest.permission.GET_ACCOUNTS
                                )

                                requestPermissions(
                                    permissionsList.toTypedArray(),
                                    EntryConfigurations.PermissionRequestCode
                                )

                            }

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

        if (Firebase.auth.currentUser == null) {

            SnackbarBuilder(applicationContext).show(
                rootView = accountInformationLayoutBinding.rootView,
                messageText = getString(R.string.anonymouslySignInError),
                messageDuration = Snackbar.LENGTH_INDEFINITE,
                actionButtonText = R.string.signInText,
                snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                    override fun onActionButtonClicked(snackbar: Snackbar) {
                        super.onActionButtonClicked(snackbar)
                        Log.d(this@AccountInformation.javaClass.simpleName, "Back Button Pressed. Firebase User Is Null. Retry...")

                        userInformation.startSignInProcess()

                    }

                }
            )

        } else {

            if (profileUpdating) {

                profileUpdating = false

                this@AccountInformation.finish()

            } else {

                this@AccountInformation.finish()

            }

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {

            when (requestCode) {
                UserInformation.GoogleSignInRequestCode -> {

                    val googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                    googleSignInAccountTask.addOnSuccessListener {

                        val googleSignInAccount = googleSignInAccountTask.getResult(ApiException::class.java)

                        val emailAddress = googleSignInAccount.email

                        if (emailAddress != null) {

                            Firebase.auth.fetchSignInMethodsForEmail(emailAddress)
                                .addOnSuccessListener { signInMethodQuery ->
                                    Log.d(this@AccountInformation.javaClass.simpleName, "Current Sign In Methods: ${signInMethodQuery.signInMethods.toString()}")

                                    val authenticationCredential = GoogleAuthProvider.getCredential(googleSignInAccount?.idToken, null)

                                    Firebase.auth.currentUser?.linkWithCredential(authenticationCredential)?.addOnSuccessListener {
                                        Log.d(this@AccountInformation.javaClass.simpleName, "Anonymous Credential Linked With Google Account Credential.")

                                        val userProfileChangeRequestBuilder = UserProfileChangeRequest.Builder().apply {
                                            displayName = googleSignInAccount.displayName
                                            photoUri = googleSignInAccount.photoUrl
                                        }

                                        Firebase.auth.currentUser?.updateProfile(userProfileChangeRequestBuilder.build())
                                            ?.addOnSuccessListener {
                                                Log.d(this@AccountInformation.javaClass.simpleName, "Firebase User Profile Updated.")

                                                Firebase.auth.currentUser?.let { linkedInFirebaseUser ->

                                                    val accountName: String = linkedInFirebaseUser.email.toString()

                                                    userInformationIO.saveUserInformation(accountName)

                                                    createUserProfile()

                                                    databaseCheckpoint(linkedInFirebaseUser)

                                                }

                                            }

                                    }?.addOnFailureListener {
                                        it.printStackTrace()

                                        when (it) {
                                            is FirebaseAuthUserCollisionException -> {
                                                Log.w(this@AccountInformation.javaClass.simpleName, it.message.orEmpty())

                                                Firebase.auth.currentUser?.delete()?.addOnSuccessListener {

                                                    Firebase.auth.signInWithCredential(authenticationCredential)
                                                        .addOnSuccessListener { authResult ->

                                                            authResult.user?.let { signedInFirebaseUser ->

                                                                val accountName: String = signedInFirebaseUser.email.toString()

                                                                userInformationIO.saveUserInformation(accountName)
                                                                userInformationIO.saveNewFirebaseUniqueIdentifier(signedInFirebaseUser.uid)

                                                                userInformationIO.saveNewUserInformation(authResult.additionalUserInfo?.isNewUser?:false)

                                                                createUserProfile()

                                                                databaseCheckpoint(signedInFirebaseUser)

                                                            }

                                                        }.addOnFailureListener {



                                                        }

                                                }

                                            }
                                        }

                                    }

                                }.addOnFailureListener {
                                    it.printStackTrace()
                                }

                        } else {

                            Toast.makeText(applicationContext, getString(R.string.waitingInformation), Toast.LENGTH_LONG).show()

                            Handler(Looper.getMainLooper()).postDelayed({

                                userInformation.startSignInProcess()

                            }, 777)

                        }

                    }

                }
                else -> {

                }
            }

        }

    }

    override fun networkAvailable() {

    }

    override fun networkLost() {

    }

    private fun databaseCheckpoint(firebaseUser: FirebaseUser) {

        val notesIO = NotesIO(application as KeepNoteApplication)

        if (!userInformationIO.userIsReturning()) {
            // User Linked Anonymous Account With Google Account

            notesIO.insertAllNotesIntoCloudDatabase(this@AccountInformation, firebaseUser)

        } else {
            // User Signed In To Existing Google Account & Anonymous Account Deleted

            notesIO.startBackgroundMigrationProcess(this@AccountInformation, firebaseUser)

        }

    }

}