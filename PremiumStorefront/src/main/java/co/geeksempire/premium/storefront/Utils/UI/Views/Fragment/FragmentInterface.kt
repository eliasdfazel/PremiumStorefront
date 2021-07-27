/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/15/21, 11:48 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.Fragment

interface FragmentInterface {
    fun fragmentCreated(applicationPackageName: String, applicationName: String, applicationSummary: String) {}
    fun fragmentDestroyed() {}
}