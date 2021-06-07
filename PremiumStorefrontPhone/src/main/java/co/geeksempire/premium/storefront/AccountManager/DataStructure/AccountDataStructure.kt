/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/7/21, 9:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.DataStructure

import com.google.firebase.firestore.FieldValue

data class UserInformationProfileData (var privacyAgreement: Boolean? = false,
                                       var userIdentification: String, var userEmailAddress: String, var userDisplayName: String, var userProfileImage: String,
                                       var instagramAccount: String?,
                                       var twitterAccount: String?,
                                       var phoneNumber: String?,
                                       var phoneNumberVerified: Boolean? = false,
                                       var userJointDate: FieldValue = FieldValue.serverTimestamp(),
                                       var isBetaUser: Boolean = false)

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
        const val userLatitude = "userLatitude"
        const val userLongitude = "userLongitude"
        const val userState = "userState"
        const val userLastSignIn = "userLastSignIn"
        const val userJointDate = "userJointDate"
    }

    fun userProfileDatabasePath(userUniqueIdentifier: String) : String = "PremiumStorefront/${userUniqueIdentifier}/Profile"

    fun invitedSuccessDatabasePath(invitingFriendUniqueIdentifier: String, userUniqueIdentifier: String) : String = "PremiumStorefront/${invitingFriendUniqueIdentifier}/Profile/SuccessfulInvitations/Friends/${userUniqueIdentifier}"

}