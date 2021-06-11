/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/11/21, 7:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.View

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R

class PrepareActionCenterUserInterface(private val context: Context,
                                       private val actionCenterView: ImageView,
                                       private val actionLeftView: ImageView, private val actionMiddleView: ImageView, private val actionRightView: ImageView) {

    fun design(themeType: Boolean = ThemeType.ThemeLight) {

        val backgroundLightShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

        backgroundLightShadowRadius[0] = (77).toFloat()//topLeftCorner
        backgroundLightShadowRadius[1] = (77).toFloat()//topLeftCorner

        backgroundLightShadowRadius[2] = (23).toFloat()//topRightCorner
        backgroundLightShadowRadius[3] = (23).toFloat()//topRightCorner

        backgroundLightShadowRadius[4] = (13).toFloat()//bottomRightCorner
        backgroundLightShadowRadius[5] = (13).toFloat()//bottomRightCorner

        backgroundLightShadowRadius[6] = (13).toFloat()//bottomLeftCorner
        backgroundLightShadowRadius[7] = (13).toFloat()//bottomLeftCorner

        val shapeLightShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundLightShadowRadius, null, null))
        shapeLightShadow.paint.apply {
            color = context.getColor(R.color.white)

            setShadowLayer(29f, 0f, 0f, context.getColor(R.color.white))
        }

        val backgroundDarkShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

        backgroundDarkShadowRadius[0] = (13).toFloat()//topLeftCorner
        backgroundDarkShadowRadius[1] = (13).toFloat()//topLeftCorner

        backgroundDarkShadowRadius[2] = (13).toFloat()//topRightCorner
        backgroundDarkShadowRadius[3] = (13).toFloat()//topRightCorner

        backgroundDarkShadowRadius[4] = (77).toFloat()//bottomRightCorner
        backgroundDarkShadowRadius[5] = (77).toFloat()//bottomRightCorner

        backgroundDarkShadowRadius[6] = (23).toFloat()//bottomLeftCorner
        backgroundDarkShadowRadius[7] = (23).toFloat()//bottomLeftCorner

        val shapeDarkShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundDarkShadowRadius, null, null))
        shapeDarkShadow.paint.apply {
            color = context.getColor(R.color.black)

            setShadowLayer(29f, 0f, 0f, context.getColor(R.color.black_transparent))
        }

        val shadowLayer = context.getDrawable(R.drawable.action_center_shadow_background_light) as LayerDrawable

        actionCenterView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeDarkShadow.paint)

        shadowLayer.setDrawableByLayerId(R.id.lightShadow, shapeLightShadow)
        shadowLayer.setDrawableByLayerId(R.id.darkShadow, shapeDarkShadow)

        val foregroundLayer = shadowLayer.findDrawableByLayerId(R.id.foregroundLayer)
        foregroundLayer.setTint(when (themeType) {
            ThemeType.ThemeLight -> {
                context.getColor(R.color.premiumLight)
            }
            ThemeType.ThemeDark -> {
                context.getColor(R.color.premiumDark)
            }
            else -> context.getColor(R.color.premiumLight)
        })

        actionCenterView.setImageDrawable(shadowLayer)

        actionCenterView.bringToFront()

    }

    fun setupIconsForStorefront(themeType: Boolean = ThemeType.ThemeLight) {

        val actionCenterLeft = context.getDrawable(R.drawable.action_center_left) as LayerDrawable
        actionCenterLeft.setDrawableByLayerId(R.id.actionCenterLeftIcon, context.getDrawable(R.drawable.sort_icon)?.apply {
            setTint(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.default_color_dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.default_color_light)
                }
                else -> context.getColor(R.color.default_color_dark)
            })
        })
        actionLeftView.setImageDrawable(actionCenterLeft)
        actionLeftView.contentDescription = context.getString(R.string.sortActionDescription)
        actionLeftView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        actionLeftView.setOnLongClickListener {

            Toast.makeText(context, it.contentDescription, Toast.LENGTH_LONG).show()

            true
        }

        val actionCenterMiddle = context.getDrawable(R.drawable.action_center_middle) as LayerDrawable
        actionCenterMiddle.setDrawableByLayerId(R.id.actionCenterMiddleIcon, context.getDrawable(R.drawable.search_icon)?.apply {
            setTint(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.default_color_dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.default_color_light)
                }
                else -> context.getColor(R.color.default_color_dark)
            })
        })
        actionMiddleView.setImageDrawable(actionCenterMiddle)
        actionMiddleView.contentDescription = context.getString(R.string.searchActionDescription)
        actionMiddleView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        actionMiddleView.setOnLongClickListener {

            Toast.makeText(context, it.contentDescription, Toast.LENGTH_LONG).show()

            true
        }

        val actionCenterRight = context.getDrawable(R.drawable.action_center_right) as LayerDrawable
        actionCenterRight.setDrawableByLayerId(R.id.actionCenterRightIcon, context.getDrawable(R.drawable.filter_icon)?.apply {
            setTint(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.default_color_dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.default_color_light)
                }
                else -> context.getColor(R.color.default_color_dark)
            })
        })
        actionRightView.setImageDrawable(actionCenterRight)
        actionRightView.contentDescription = context.getString(R.string.filterActionDescription)
        actionRightView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        actionRightView.setOnLongClickListener {

            Toast.makeText(context, it.contentDescription, Toast.LENGTH_LONG).show()

            true
        }

    }

    fun setupIconsForDetails(themeType: Boolean = ThemeType.ThemeLight) {

        val actionCenterLeft = context.getDrawable(R.drawable.action_center_left) as LayerDrawable
        actionCenterLeft.setDrawableByLayerId(R.id.actionCenterLeftIcon, context.getDrawable(R.drawable.share_icon)?.apply {
            setTint(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.default_color_dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.default_color_light)
                }
                else -> context.getColor(R.color.default_color_dark)
            })
        })
        actionLeftView.setImageDrawable(actionCenterLeft)
        actionLeftView.contentDescription = context.getString(R.string.shareActionDescription)
        actionLeftView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        actionLeftView.setOnLongClickListener {

            Toast.makeText(context, it.contentDescription, Toast.LENGTH_LONG).show()

            true
        }

        val actionCenterMiddle = context.getDrawable(R.drawable.action_center_middle) as LayerDrawable
        actionCenterMiddle.setDrawableByLayerId(R.id.actionCenterMiddleIcon, context.getDrawable(R.drawable.install_icon)?.apply {
            setTint(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.default_color_dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.default_color_light)
                }
                else -> context.getColor(R.color.default_color_dark)
            })
        })
        actionMiddleView.setImageDrawable(actionCenterMiddle)
        actionMiddleView.contentDescription = context.getString(R.string.installActionDescription)
        actionMiddleView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        actionMiddleView.setOnLongClickListener {

            Toast.makeText(context, it.contentDescription, Toast.LENGTH_LONG).show()

            true
        }

        val actionCenterRight = context.getDrawable(R.drawable.action_center_right) as LayerDrawable
        actionCenterRight.setDrawableByLayerId(R.id.actionCenterRightIcon, context.getDrawable(R.drawable.rate_icon)?.apply {
            setTint(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.default_color_dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.default_color_light)
                }
                else -> context.getColor(R.color.default_color_dark)
            })
        })
        actionRightView.setImageDrawable(actionCenterRight)
        actionRightView.contentDescription = context.getString(R.string.rateActionDescription)
        actionRightView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        actionRightView.setOnLongClickListener {

            Toast.makeText(context, it.contentDescription, Toast.LENGTH_LONG).show()

            true
        }

    }

    fun setupIconsForCategoryDetails() {

    }

}