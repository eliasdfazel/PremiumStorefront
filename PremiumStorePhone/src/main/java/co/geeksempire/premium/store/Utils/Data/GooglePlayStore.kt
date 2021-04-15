/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/15/21 10:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.store.Utils.Data

fun generateGooglePlayStoreDownloadLink(aPackageName: String) : String {

    return "https://play.google.com/store/apps/details?id=${aPackageName}&rdid=${aPackageName}"
}