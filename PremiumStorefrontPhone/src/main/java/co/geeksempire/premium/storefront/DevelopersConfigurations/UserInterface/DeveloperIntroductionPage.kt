/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/12/21, 8:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.databinding.DeveloperIntroductionLayoutBinding

class DeveloperIntroductionPage : AppCompatActivity() {

    lateinit var developerIntroductionLayoutBinding: DeveloperIntroductionLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        developerIntroductionLayoutBinding = DeveloperIntroductionLayoutBinding.inflate(layoutInflater)
        setContentView(developerIntroductionLayoutBinding.root)

    }

}