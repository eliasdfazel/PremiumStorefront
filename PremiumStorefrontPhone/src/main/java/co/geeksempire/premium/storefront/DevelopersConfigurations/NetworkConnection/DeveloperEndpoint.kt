/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 6:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.NetworkConnection

import co.geeksempire.premium.storefront.Database.GeneralConfigurations.RealtimeDatabaseConfiguration
import com.google.firebase.database.DatabaseReference

class DeveloperEndpoint {

    val realtimeDatabaseConfiguration = RealtimeDatabaseConfiguration()

    fun developerDatabaseEndpoint (developerName: String) : DatabaseReference =
        realtimeDatabaseConfiguration.realtimeDatabaseReference
            .child("PremiumStorefrontChoices")
            .child("Developers")
            .child(developerName)

}