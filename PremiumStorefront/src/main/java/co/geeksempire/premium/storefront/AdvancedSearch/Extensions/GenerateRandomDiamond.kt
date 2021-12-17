/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/17/21, 3:21 AM
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

    val positionRange = IntRange(0, 137)
    val scaleRange = IntRange(30, 70)

    val randomAmount = 7

    when (themeType) {
        ThemeType.ThemeLight -> {

            repeat(randomAmount) { index ->

                val diamondImageView = DiamondRandomImageViewBinding.inflate(layoutInflater)
                diamondImageView.diamondImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_bright))

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

            repeat(randomAmount) { index ->

                val diamondImageView = DiamondRandomImageViewBinding.inflate(layoutInflater)
                diamondImageView.diamondImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color))

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
        else -> {

            repeat(randomAmount) { index ->

                val diamondImageView = DiamondRandomImageViewBinding.inflate(layoutInflater)
                diamondImageView.diamondImageView.imageTintList = ColorStateList.valueOf(getColor(R.color.default_color_bright_transparent))

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