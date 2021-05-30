/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/30/21, 11:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.DataStructure

class AccountDataStructure {

    fun userProfileDatabasePath(userUniqueIdentifier: String) : String = "PremiumStorefront/${userUniqueIdentifier}/Profile"

}