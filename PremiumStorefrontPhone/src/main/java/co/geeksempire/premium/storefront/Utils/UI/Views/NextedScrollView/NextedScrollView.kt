/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/16/21, 2:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.NextedScrollView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import co.geeksempire.premium.storefront.R


class NextedScrollView(context: Context, attributesSet: AttributeSet) : NestedScrollView(context, attributesSet) {

    var attributeSet: TypedArray = context.obtainStyledAttributes(attributesSet, R.styleable.NextedScrollView)

    val isScrollable: Boolean = true

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {

        println(">>>>>>>>>>>> " + velocityY)

//        val yVelocity = (velocityY/5).toInt()
//
//        fling(yVelocity)
//
//        smoothScrollTo(0, 5, 3579)

//        return super.onNestedFling(target, velocityX, yVelocity.toFloat(), consumed)
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {

                println(">>> down")

                isScrollable && super.onTouchEvent(motionEvent)

            }
            MotionEvent.ACTION_MOVE -> {

                println(">>> move")

                isScrollable && super.onTouchEvent(motionEvent)

            }
            else -> {
                super.onTouchEvent(motionEvent)
            }
        }
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent?): Boolean {

        return isScrollable && super.onInterceptTouchEvent(motionEvent)
    }

}