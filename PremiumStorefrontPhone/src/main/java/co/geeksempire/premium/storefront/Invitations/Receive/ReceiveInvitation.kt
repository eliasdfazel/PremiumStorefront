/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/7/21, 9:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Invitations.Receive

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.AccountManager.DataStructure.AccountDataStructure
import co.geeksempire.premium.storefront.EntryConfigurations
import co.geeksempire.premium.storefront.PremiumStorefrontApplication
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import co.geeksempire.premium.storefront.databinding.ReceiveInvitationLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import net.geeksempire.ready.keep.notes.Invitations.Utils.InvitationConstant

class ReceiveInvitation : AppCompatActivity() {

    val firebaseUser = Firebase.auth.currentUser

    val accountDataStructure = AccountDataStructure()

    lateinit var receiveInvitationLayoutBinding: ReceiveInvitationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiveInvitationLayoutBinding = ReceiveInvitationLayoutBinding.inflate(layoutInflater)
        setContentView(receiveInvitationLayoutBinding.root)

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this@ReceiveInvitation) { pendingDynamicLinkData ->

                pendingDynamicLinkData?.let {

                    pendingDynamicLinkData.link?.also { dynamicLinkUri ->

                        if (Firebase.auth.currentUser != null) {

                            startActivity(Intent(applicationContext, Storefront::class.java).apply {

                            }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                            this@ReceiveInvitation.finish()

                        } else {

                            firebaseUser?.let { firebaseUser ->

                                //Data Of User Who Invited This User
                                val uniqueUserId = dynamicLinkUri.getQueryParameter(InvitationConstant.UniqueUserId)!!
                                val userDisplayName = dynamicLinkUri.getQueryParameter(InvitationConstant.UserDisplayName)!!
                                val userProfileImage = dynamicLinkUri.getQueryParameter(InvitationConstant.UserProfileImage)!!

                                val invitedFriend = LinkedHashMap<String, String>()
                                invitedFriend[InvitationConstant.UniqueUserId] = firebaseUser.uid
                                invitedFriend[InvitationConstant.UserEmailAddress] = firebaseUser.email.toString()
                                invitedFriend[InvitationConstant.UserDisplayName] = firebaseUser.displayName.toString()
                                invitedFriend[InvitationConstant.UserProfileImage] = firebaseUser.photoUrl.toString()

                                if (firebaseUser.uid != uniqueUserId) {

                                    (application as PremiumStorefrontApplication).firestoreDatabase
                                        .document(accountDataStructure.invitedSuccessDatabasePath(invitingFriendUniqueIdentifier = firebaseUser.uid, userUniqueIdentifier = uniqueUserId))
                                        .set(invitedFriend)
                                        .addOnSuccessListener {

                                            SnackbarBuilder(applicationContext).show (
                                                rootView = receiveInvitationLayoutBinding.rootView,
                                                messageText= getString(R.string.receiveInvitationText),
                                                messageDuration = Snackbar.LENGTH_INDEFINITE,
                                                actionButtonText = R.string.proceedText,
                                                snackbarActionHandlerInterface = object :
                                                    SnackbarActionHandlerInterface {

                                                    override fun onActionButtonClicked(snackbar: Snackbar) {
                                                        super.onActionButtonClicked(snackbar)

                                                        startActivity(Intent(applicationContext, EntryConfigurations::class.java).apply {

                                                        }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                                                        this@ReceiveInvitation.finish()

                                                    }

                                                }
                                            )

                                        }.addOnFailureListener {



                                        }

                                }

                            }

                        }

                    }

                }

            }.addOnFailureListener(this) { exception ->
                exception.printStackTrace()

            }

    }

}