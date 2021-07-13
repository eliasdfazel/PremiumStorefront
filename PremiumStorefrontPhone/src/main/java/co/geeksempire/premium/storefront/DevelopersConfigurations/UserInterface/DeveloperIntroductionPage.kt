/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/13/21, 10:06 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.DevelopersConfigurations.DataStructure.DevelopersDataKey
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.Extensions.setupUserInterfaceDeveloperPage
import co.geeksempire.premium.storefront.databinding.DeveloperIntroductionLayoutBinding

class DeveloperIntroductionPage : AppCompatActivity() {

    lateinit var developerIntroductionLayoutBinding: DeveloperIntroductionLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        developerIntroductionLayoutBinding = DeveloperIntroductionLayoutBinding.inflate(layoutInflater)
        setContentView(developerIntroductionLayoutBinding.root)

        setupUserInterfaceDeveloperPage()

        if (intent != null) {

            val developerName = intent.getStringExtra(DevelopersDataKey.DeveloperName)
            val developerDescription = intent.getStringExtra(DevelopersDataKey.DeveloperDescription)

            val developerLogo = intent.getStringExtra(DevelopersDataKey.DeveloperLogo)
            val developerCoverImage = intent.getStringExtra(DevelopersDataKey.DeveloperCoverImage)

            val developerCountry = intent.getStringExtra(DevelopersDataKey.DeveloperCountry)
            val developerCountryFlag = intent.getStringExtra(DevelopersDataKey.DeveloperCountryFlag)

            val developerEmail = intent.getStringExtra(DevelopersDataKey.DeveloperEmail)
            val developerWebsite = intent.getStringExtra(DevelopersDataKey.DeveloperWebsite)

            val developerSocialMedia = intent.getStringExtra(DevelopersDataKey.DeveloperSocialMedia)
            val developerSocialMediaLink = intent.getStringExtra(DevelopersDataKey.DeveloperSocialMediaLink)

            val productsApplicationsId = if (intent.hasExtra(DevelopersDataKey.DeveloperApplications)) { intent.getStringExtra(DevelopersDataKey.DeveloperApplications) } else { null }
            val productsGamesId = if (intent.hasExtra(DevelopersDataKey.DeveloperGames)) { intent.getStringExtra(DevelopersDataKey.DeveloperGames) } else { null }
            val productsBooksId = if (intent.hasExtra(DevelopersDataKey.DeveloperBooks)) { intent.getStringExtra(DevelopersDataKey.DeveloperBooks) } else { null }
            val developerMoviesId = if (intent.hasExtra(DevelopersDataKey.DeveloperMovies)) { intent.getStringExtra(DevelopersDataKey.DeveloperMovies) } else { null }



        } else {

            this@DeveloperIntroductionPage.finish()

        }

    }

}