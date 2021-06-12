/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/12/21, 12:11 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Extensions

import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.UserInterface.FavoriteProducts
import co.geeksempire.premium.storefront.R

fun FavoriteProducts.setupFavoritedUserInterface(themeType: Boolean = ThemeType.ThemeLight) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            window.statusBarColor = getColor(R.color.premiumLight)
            window.navigationBarColor = getColor(R.color.premiumLight)

            favoriteProductsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            favoriteProductsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

        }
        ThemeType.ThemeDark -> {

            window.statusBarColor = getColor(R.color.premiumDark)
            window.navigationBarColor = getColor(R.color.premiumDark)

            favoriteProductsLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            favoriteProductsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_dark))

        }
        else -> {

            window.statusBarColor = getColor(R.color.premiumLight)
            window.navigationBarColor = getColor(R.color.premiumLight)

            favoriteProductsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

            favoriteProductsLayoutBinding.goBackView.setImageDrawable(getDrawable(R.drawable.go_back_layer_light))

        }
    }

}