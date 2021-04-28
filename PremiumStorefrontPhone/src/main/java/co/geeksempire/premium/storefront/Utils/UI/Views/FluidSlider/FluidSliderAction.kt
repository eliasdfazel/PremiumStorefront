/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/27/21 10:55 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.FluidSlider

import android.util.Log

interface FluidSliderActionInterface {
    fun fluidSliderPosition(sliderPosition: Float) {}

    fun fluidSliderStarted() {}
    fun fluidSliderFinished() {}
}

class FluidSliderAction (private val fluidSliderActionInterface: FluidSliderActionInterface) {

    fun startListener(fluidSlider: FluidSlider) {

        fluidSlider.positionListener = { sliderPosition ->
            Log.d(this@FluidSliderAction.javaClass.simpleName, "$sliderPosition")

            fluidSlider.bubbleText = "${0 + (7  * sliderPosition).toInt()}"

            fluidSliderActionInterface.fluidSliderPosition(sliderPosition)
        }

        fluidSlider.beginTrackingListener = {

            fluidSliderActionInterface.fluidSliderStarted()

        }

        fluidSlider.endTrackingListener = {

            fluidSliderActionInterface.fluidSliderFinished()

        }

    }

}