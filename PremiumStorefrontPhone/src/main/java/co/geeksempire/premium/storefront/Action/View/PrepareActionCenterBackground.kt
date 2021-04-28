/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/28/21 12:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Action.View

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.R

class PrepareActionCenterBackground(private val context: Context, private val actionCenterView: ImageView,
    private val actionLeftView: ImageView, private val actionMiddleView: ImageView, private val actionRightView: ImageView) {

    fun start() {

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
            color = context.getColor(R.color.dark_transparent)

            setShadowLayer(27f, 0f, 0f, context.getColor(R.color.dark_transparent))
        }

        val shadowLayer = context.getDrawable(R.drawable.action_center_shadow_background) as LayerDrawable

        actionCenterView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeLightShadow.paint)
        actionCenterView.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeDarkShadow.paint)

        shadowLayer.setDrawableByLayerId(R.id.lightShadow, shapeLightShadow)
        shadowLayer.setDrawableByLayerId(R.id.darkShadow, shapeDarkShadow)

        shadowLayer.findDrawableByLayerId(R.id.foregroundLayer).setTint(context.getColor(R.color.premiumLight))

        actionCenterView.setImageDrawable(shadowLayer)

    }

}