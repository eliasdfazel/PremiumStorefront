/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/9/21, 8:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.DataStructure

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreferencesLiveData : ViewModel() {

    /**
     * True To Force Reset Theme
     **/
    val toggleTheme: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

}