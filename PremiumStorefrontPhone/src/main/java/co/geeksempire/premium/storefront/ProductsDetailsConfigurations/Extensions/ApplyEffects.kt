/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/11/21, 10:32 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R

fun ProductDetailsFragment.applyShadowEffectsForContentBackground(themeType: Boolean = ThemeType.ThemeLight) {

    /* Start - Add Shadow To Content Background */
    val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    backgroundShadowRadius[0] = (37).toFloat()//topLeftCorner
    backgroundShadowRadius[1] = (37).toFloat()//topLeftCorner

    backgroundShadowRadius[2] = (37).toFloat()//topRightCorner
    backgroundShadowRadius[3] = (37).toFloat()//topRightCorner

    backgroundShadowRadius[4] = (0).toFloat()//bottomRightCorner
    backgroundShadowRadius[5] = (0).toFloat()//bottomRightCorner

    backgroundShadowRadius[6] = (0).toFloat()//bottomLeftCorner
    backgroundShadowRadius[7] = (0).toFloat()//bottomLeftCorner

    val shapeShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))

    val shadowLayer = when (themeType) {
        ThemeType.ThemeLight -> {

            productDetailsLayoutBinding.rootViewFragment.setBackgroundColor(requireContext().getColor(R.color.premiumLight))

            productDetailsLayoutBinding.goBackView.setImageDrawable(requireContext().getDrawable(R.drawable.go_back_layer_light))

            productDetailsLayoutBinding.applicationNameTextView.setTextColor(requireContext().getColor(R.color.dark))
            productDetailsLayoutBinding.applicationDescriptionTextView.setTextColor(requireContext().getColor(R.color.dark))

            productDetailsLayoutBinding.applicationRatingImageView.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.light))

            productDetailsLayoutBinding.applicationIconImageView.background = requireContext().getDrawable(R.drawable.product_icon_dimension_effect_light)

            shapeShadow.paint.apply {
                color = requireContext().getColor(R.color.dark)

                setShadowLayer(31f, 0f, 0f, requireContext().getColor(R.color.dark_transparent_high))
            }

            requireContext().getDrawable(R.drawable.product_details_background_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            productDetailsLayoutBinding.rootViewFragment.setBackgroundColor(requireContext().getColor(R.color.premiumDark))

            productDetailsLayoutBinding.goBackView.setImageDrawable(requireContext().getDrawable(R.drawable.go_back_layer_dark))

            productDetailsLayoutBinding.applicationNameTextView.setTextColor(requireContext().getColor(R.color.light))
            productDetailsLayoutBinding.applicationDescriptionTextView.setTextColor(requireContext().getColor(R.color.light))

            productDetailsLayoutBinding.applicationRatingImageView.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.dark))

            productDetailsLayoutBinding.applicationIconImageView.background = requireContext().getDrawable(R.drawable.product_icon_dimension_effect_dark)

            shapeShadow.paint.apply {
                color = requireContext().getColor(R.color.black)

                setShadowLayer(31f, 0f, 0f, requireContext().getColor(R.color.black_transparent))
            }

            requireContext().getDrawable(R.drawable.product_details_background_dark) as LayerDrawable

        }
        else -> {

            productDetailsLayoutBinding.rootViewFragment.setBackgroundColor(requireContext().getColor(R.color.premiumLight))

            productDetailsLayoutBinding.goBackView.setImageDrawable(requireContext().getDrawable(R.drawable.go_back_layer_light))

            productDetailsLayoutBinding.applicationNameTextView.setTextColor(requireContext().getColor(R.color.dark))
            productDetailsLayoutBinding.applicationDescriptionTextView.setTextColor(requireContext().getColor(R.color.dark))

            productDetailsLayoutBinding.applicationRatingImageView.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.light))

            productDetailsLayoutBinding.applicationIconImageView.background = requireContext().getDrawable(R.drawable.product_icon_dimension_effect_light)

            shapeShadow.paint.apply {
                color = requireContext().getColor(R.color.dark)

                setShadowLayer(31f, 0f, 0f, requireContext().getColor(R.color.dark_transparent_high))
            }

            requireContext().getDrawable(R.drawable.product_details_background_light) as LayerDrawable
        }
    }

    shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

    productDetailsLayoutBinding.allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
    productDetailsLayoutBinding.allContentBackground.background = (shadowLayer)
    /* End - Add Shadow To Content Background */

}

fun ProductDetailsFragment.applyGlowingEffectsForRatingBackground(themeType: Boolean = ThemeType.ThemeLight, glowingColor: Int) {

    /* Start - Setup Negative Space Of Rating Element */
    val negativeSpaceLayers = requireContext().getDrawable(R.drawable.application_rating_details_glowing_frame) as LayerDrawable

    val negativeSpaceLayersShape = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    negativeSpaceLayersShape[0] = (199).toFloat()//topLeftCorner
    negativeSpaceLayersShape[1] = (199).toFloat()//topLeftCorner

    negativeSpaceLayersShape[2] = (199).toFloat()//topRightCorner
    negativeSpaceLayersShape[3] = (199).toFloat()//topRightCorner

    negativeSpaceLayersShape[6] = (199).toFloat()//bottomLeftCorner
    negativeSpaceLayersShape[7] = (199).toFloat()//bottomLeftCorner

    negativeSpaceLayersShape[4] = (199).toFloat()//bottomRightCorner
    negativeSpaceLayersShape[5] = (199).toFloat()//bottomRightCorner

    val shapeShadowNegativeSpace: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceLayersShape, null, null))
    shapeShadowNegativeSpace.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    negativeSpaceLayers.findDrawableByLayerId(R.id.radialGradientBackground).setTint(glowingColor)
    negativeSpaceLayers.findDrawableByLayerId(R.id.circleIconFrame).setTint(glowingColor)

    negativeSpaceLayers.setDrawableByLayerId(R.id.clearLayer, shapeShadowNegativeSpace)

    productDetailsLayoutBinding.applicationRatingTextView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadowNegativeSpace.paint)
    productDetailsLayoutBinding.applicationRatingTextView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    productDetailsLayoutBinding.applicationRatingTextView.background = negativeSpaceLayers
    /* End - Setup Negative Space Of Rating Element */

}

fun ProductDetailsFragment.applyNegativeSpaceEffectsForFavorite(themeType: Boolean = ThemeType.ThemeLight) {

    val favoriteLayer = when (themeType) {
        ThemeType.ThemeLight -> {

            requireContext().getDrawable(R.drawable.favorite_layer_light) as LayerDrawable

        }
        ThemeType.ThemeDark -> {

            requireContext().getDrawable(R.drawable.favorite_layer_dark) as LayerDrawable

        }
        else -> requireContext().getDrawable(R.drawable.favorite_layer_light) as LayerDrawable
    }

    val negativeSpaceLeft = floatArrayOf(
        0f,//topLeftCorner
        0f,//topLeftCorner

        51f,//topRightCorner
        51f,//topRightCorner

        0f,//bottomLeftCorner
        0f,//bottomLeftCorner

        0f,//bottomRightCorner
        0f//bottomRightCorner
    )

    val shapeNegativeSpaceLeft: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceLeft, null, null))

    shapeNegativeSpaceLeft.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    val negativeSpaceRight = floatArrayOf(
        51f,//topLeftCorner
        51f,//topLeftCorner

        0f,//topRightCorner
        0f,//topRightCorner

        0f,//bottomLeftCorner
        0f,//bottomLeftCorner

        0f,//bottomRightCorner
        0f//bottomRightCorner
    )

    val shapeNegativeSpaceRight: ShapeDrawable = ShapeDrawable(RoundRectShape(negativeSpaceRight, null, null))

    shapeNegativeSpaceRight.paint.apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true

        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    favoriteLayer.setDrawableByLayerId(R.id.leftClearLayer, shapeNegativeSpaceLeft)
    favoriteLayer.setDrawableByLayerId(R.id.rightClearLayer, shapeNegativeSpaceRight)

    productDetailsLayoutBinding.favoriteView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeNegativeSpaceLeft.paint)
    productDetailsLayoutBinding.favoriteView.setLayerType(AppCompatButton.LAYER_TYPE_HARDWARE, null)

    productDetailsLayoutBinding.favoriteView.background = (favoriteLayer)

}