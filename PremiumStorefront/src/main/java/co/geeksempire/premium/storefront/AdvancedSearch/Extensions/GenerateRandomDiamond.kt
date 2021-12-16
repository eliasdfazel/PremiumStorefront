/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/16/21, 9:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.Extensions

import android.content.res.ColorStateList
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.databinding.DiamondRandomImageViewBinding

fun CompleteSearch.generateRandomDiamond(themeType: Boolean) {

    val positionRange = IntRange(0, 100)
    val scaleRange = IntRange(30, 90)

    when (themeType) {
        ThemeType.ThemeLight -> {

            repeat(7) { index ->

                val diamondImageView = DiamondRandomImageViewBinding.inflate(layoutInflater)
                diamondImageView.diamondImageView.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_light))
                diamondImageView.diamondImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.premiumDarkTransparent))

                val scaleMultiplier = (scaleRange.random().toFloat() / 100)
                diamondImageView.diamondImageView.scaleX = scaleMultiplier
                diamondImageView.diamondImageView.scaleY = scaleMultiplier

                completeSearchLayoutBinding.randomDiamondBackground.addView(diamondImageView.root)

                val newVerticalBias = (positionRange.random().toFloat() / 100)
                val newHorizontalBias = (positionRange.random().toFloat() / 100)

                val layoutParameters = diamondImageView.root.layoutParams as ConstraintLayout.LayoutParams
                layoutParameters.apply {
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    verticalBias = newVerticalBias
                    horizontalBias = newHorizontalBias
                }
                diamondImageView.root.layoutParams = layoutParameters

            }

        }
        ThemeType.ThemeDark -> {

            repeat(5) { index ->

                val diamondImageView = DiamondRandomImageViewBinding.inflate(layoutInflater)
                diamondImageView.diamondImageView.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_dark))
                diamondImageView.diamondImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.premiumLightTransparent))

                val scaleMultiplier = (scaleRange.random().toFloat() / 100)
                diamondImageView.diamondImageView.scaleX = scaleMultiplier
                diamondImageView.diamondImageView.scaleY = scaleMultiplier

                completeSearchLayoutBinding.randomDiamondBackground.addView(diamondImageView.root)

                val newVerticalBias = (positionRange.random().toFloat() / 100)
                val newHorizontalBias = (positionRange.random().toFloat() / 100)

                val layoutParameters = diamondImageView.root.layoutParams as ConstraintLayout.LayoutParams
                layoutParameters.apply {
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    verticalBias = newVerticalBias
                    horizontalBias = newHorizontalBias
                }
                diamondImageView.root.layoutParams = layoutParameters

            }

        }
    }

}