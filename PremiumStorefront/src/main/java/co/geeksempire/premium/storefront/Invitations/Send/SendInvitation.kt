/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/18/21, 5:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Invitations.Send

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.text.Html
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Data.generateHashTag
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import net.geeksempire.ready.keep.notes.Invitations.Utils.InvitationConstant
import net.geeksempire.ready.keep.notes.Invitations.Utils.ShareIt

class SendInvitation (val context: AppCompatActivity, val rootView: ViewGroup) {

    fun invite(firebaseUser: FirebaseUser) {

        val dynamicLink = Firebase.dynamicLinks.dynamicLink {

            link = Uri.parse("https://www.geeksempire.co/PremiumStorefrontInvitation")
                .buildUpon()
                .appendQueryParameter(InvitationConstant.UniqueUserId, firebaseUser.uid)
                .appendQueryParameter(InvitationConstant.UserEmailAddress, firebaseUser.email)
                .appendQueryParameter(InvitationConstant.UserDisplayName, firebaseUser.displayName)
                .appendQueryParameter(InvitationConstant.UserProfileImage, firebaseUser.photoUrl.toString())
                .build()

            domainUriPrefix = "https://premiumstorefront.page.link"

            socialMetaTagParameters {

            }

            androidParameters(context.packageName) {

            }

            iosParameters(context.packageName) {

            }

        }

        val dynamicLinkUri = dynamicLink.uri

        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newHtmlText(
            firebaseUser.displayName,
            generateInvitationText(firebaseUser.displayName!!, context.getString(R.string.applicationName), context.getString(R.string.applicationSummary), dynamicLinkUri),
            generateInvitationText(firebaseUser.displayName!!, context.getString(R.string.applicationName), context.getString(R.string.applicationSummary), dynamicLinkUri)
        )
        clipboardManager.setPrimaryClip(clipData).also {

            SnackbarBuilder(context).show (
                rootView = rootView,
                messageText= context.getString(R.string.invitationDataReady),
                messageDuration = Snackbar.LENGTH_INDEFINITE,
                actionButtonText = R.string.inviteAction,
                snackbarActionHandlerInterface = object : SnackbarActionHandlerInterface {

                    override fun onActionButtonClicked(snackbar: Snackbar) {
                        super.onActionButtonClicked(snackbar)

                        ShareIt(context).invokeTextSharing(generateInvitationText(firebaseUser.displayName!!,
                            context.getString(R.string.applicationName),
                            context.getString(R.string.applicationSummary),
                            dynamicLinkUri))

                    }

                }
            )

        }

    }

    private fun generateInvitationText(invitingUsername: String, applicationName: String, applicationSummary: String, dynamicLink: Uri) : String {

        return "${invitingUsername} Invited You To" + Html.fromHtml(applicationName, Html.FROM_HTML_MODE_COMPACT).toString() +
                "\n" +
                Html.fromHtml(applicationSummary, Html.FROM_HTML_MODE_COMPACT).toString() +
                "\n" +
                dynamicLink + " | " + generateHashTag(Html.fromHtml(applicationName, Html.FROM_HTML_MODE_COMPACT).toString()) +
                generateHashTag(Html.fromHtml(applicationSummary, Html.FROM_HTML_MODE_COMPACT).toString())
    }

}