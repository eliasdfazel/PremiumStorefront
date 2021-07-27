/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/11/21, 12:08 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.DataStructure

import androidx.annotation.Keep
import com.google.firebase.firestore.FieldValue

@Keep
data class UserInformationProfileData (var privacyAgreement: Boolean? = false,
                                       var userIdentification: String, var userEmailAddress: String, var userDisplayName: String, var userProfileImage: String,
                                       var instagramAccount: String?,
                                       var twitterAccount: String?,
                                       var phoneNumber: String?,
                                       var phoneNumberVerified: Boolean? = false,
                                       var userJointDate: FieldValue = FieldValue.serverTimestamp(),
                                       var isBetaUser: Boolean = false,
                                       var isUserDeveloper: Boolean = false)

class AccountDataStructure {

    object Attributes {
        const val userIdentification = "userIdentification"
        const val userEmailAddress = "userEmailAddress"
        const val userDisplayName = "userDisplayName"
        const val userProfileImage = "userProfileImage"
        const val instagramAccount = "instagramAccount"
        const val twitterAccount = "twitterAccount"
        const val phoneNumber = "phoneNumber"
        const val phoneNumberVerified = "phoneNumberVerified"
        const val userJointDate = "userJointDate"
        const val userDeveloper = "userDeveloper"
    }

    fun userProfileDatabasePath(userUniqueIdentifier: String, userEmailAddress: String) : String =
        "/" +
        "PremiumStorefront" +
        "/" +
        "UsersInformation" +
        "/" +
        "${userUniqueIdentifier}" +
        "/" +
        "PrivateInformation" +
        "/" +
        "${userEmailAddress}" +
        "/" +
        "Profile"

    fun invitedSuccessDatabasePath(invitingFriendUniqueIdentifier: String, userUniqueIdentifier: String) : String =
        "/" +
        "PremiumStorefront" +
        "/" +
        "UsersInformation" +
        "/" +
        "${invitingFriendUniqueIdentifier}" +
        "/" +
        "PrivateInformation" +
        "/" +
        "Profile" +
        "/" +
        "SuccessfulInvitations" +
        "/" +
        "Friends" +
        "/" +
        "${userUniqueIdentifier}"

}