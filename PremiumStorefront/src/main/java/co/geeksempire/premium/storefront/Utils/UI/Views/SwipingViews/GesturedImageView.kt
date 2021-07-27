/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/22/21, 9:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.SwipingViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import co.geeksempire.premium.storefront.Utils.UI.Gesture.GestureConstants
import co.geeksempire.premium.storefront.Utils.UI.Gesture.GestureListenerConstants
import co.geeksempire.premium.storefront.Utils.UI.Gesture.GestureListenerInterface
import co.geeksempire.premium.storefront.Utils.UI.Gesture.SwipeGestureListener

class GesturedImageView(context: Context, attributeSet: AttributeSet?) : AppCompatImageView(context, attributeSet),
    GestureListenerInterface {

    private val swipeGestureListener: SwipeGestureListener by lazy {
        SwipeGestureListener(context, this@GesturedImageView)
    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent?): Boolean {

        motionEvent?.let {
            swipeGestureListener.onTouchEvent(it)
        }

        return if (motionEvent != null) {
            super.dispatchTouchEvent(motionEvent)
        } else {
            false
        }
    }



    override fun onSwipeGesture(gestureConstants: GestureConstants, downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float) {
        super.onSwipeGesture(gestureConstants, downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)

        when (gestureConstants) {
            is GestureConstants.SwipeHorizontal -> {
                when (gestureConstants.horizontalDirection) {
                    GestureListenerConstants.SWIPE_RIGHT -> {
                        Log.d(this@GesturedImageView.javaClass.simpleName, "Swipe Right")

                    }
                    GestureListenerConstants.SWIPE_LEFT -> {
                        Log.d(this@GesturedImageView.javaClass.simpleName, "Swipe Left")

                    }
                }
            }
            is GestureConstants.SwipeVertical -> {
                when (gestureConstants.verticalDirection) {
                    GestureListenerConstants.SWIPE_UP -> {
                        Log.d(this@GesturedImageView.javaClass.simpleName, "Swipe Up")

                    }
                    GestureListenerConstants.SWIPE_DOWN -> {
                        Log.d(this@GesturedImageView.javaClass.simpleName, "Swipe Down")

                    }
                }
            }
        }
    }

    companion object {

    }

}