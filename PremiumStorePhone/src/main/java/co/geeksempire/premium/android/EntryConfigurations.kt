/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/12/21 8:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.android.databinding.EntryConfigurationsLayoutBinding

class EntryConfigurations : AppCompatActivity() {

    lateinit var entryConfigurationsLayoutBinding: EntryConfigurationsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryConfigurationsLayoutBinding = EntryConfigurationsLayoutBinding.inflate(layoutInflater)
        setContentView(entryConfigurationsLayoutBinding.root)

    }

}