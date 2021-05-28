/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/28/21, 11:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AccountManager.DataStructure

class AccountDataStructure {

    companion object {

        fun userProfileDatabasePath(userUniqueIdentifier: String) : String = "PremiumStorefront/${userUniqueIdentifier}/Profile"

        fun userFavoriteDatabasePath(userUniqueIdentifier: String) : String = "PremiumStorefront/${userUniqueIdentifier}/Favorite"

    }

}