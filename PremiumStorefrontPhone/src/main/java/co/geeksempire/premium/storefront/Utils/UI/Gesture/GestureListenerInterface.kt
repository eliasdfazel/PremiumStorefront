/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/22/21, 9:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package  co.geeksempire.premium.storefront.Utils.UI.Gesture

import android.view.MotionEvent

interface GestureListenerInterface {
    fun onSwipeGesture(gestureConstants: GestureConstants, downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float) {}

    fun onSwipeGestureLeft() {}
    fun onSwipeGestureRight() {}

    fun onSwipeGestureUp() {}
    fun onSwipeGestureDown() {}

    fun onSingleTapUp(motionEvent: MotionEvent) {}
    fun onLongPress(motionEvent: MotionEvent) {}
}