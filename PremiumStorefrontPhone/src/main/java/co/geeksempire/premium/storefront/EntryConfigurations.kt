/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/15/21 4:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import co.geeksempire.premium.storefront.databinding.EntryConfigurationsLayoutBinding

class EntryConfigurations : AppCompatActivity() {

    lateinit var entryConfigurationsLayoutBinding: EntryConfigurationsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryConfigurationsLayoutBinding = EntryConfigurationsLayoutBinding.inflate(layoutInflater)
        setContentView(entryConfigurationsLayoutBinding.root)

        startActivity(Intent().apply {
            setClass(applicationContext, Storefront::class.java)
        }, ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

        this@EntryConfigurations.finish()

    }

}