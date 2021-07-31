/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/31/21, 9:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontMovies
import co.geeksempire.premium.storefront.movies.databinding.MoviesSectionsSwitcherLayoutBinding
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun StorefrontMovies.setupStorefrontMoviesUserInterface() {

    moviesSectionSwitcherDesign(this@setupStorefrontMoviesUserInterface, storefrontMoviesLayoutBinding.moviesSectionsSwitcherContainer)

}

fun moviesSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: MoviesSectionsSwitcherLayoutBinding) {

    sectionsSwitcherLayoutBinding.applicationsSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.premiumLight))

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

    sectionsSwitcherLayoutBinding.moviesSectionView.apply {

        (layoutParams as ConstraintLayout.LayoutParams).width = 0
        requestLayout()

        text = contentDescription
        setTextColor(context.getColor(R.color.white))
        iconTint = ColorStateList.valueOf(context.getColor(R.color.white))
        iconSize = dpToInteger(context, 29)
        iconPadding = dpToInteger(context, 7)
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.transparent))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))

        (layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.51f

        requestLayout()

        clearFocus()

    }

}