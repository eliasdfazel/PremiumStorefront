/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/16/21, 5:12 AM
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
import androidx.core.widget.NestedScrollView
import co.geeksempire.premium.storefront.R


class NextedScrollView(context: Context, attributesSet: AttributeSet) : NestedScrollView(context, attributesSet) {

    var attributeSet: TypedArray = context.obtainStyledAttributes(attributesSet, R.styleable.NextedScrollView)

    val isScrollable: Boolean = true

    var scrollAmount = 0

    override fun fling(velocityY: Int) {

        if (velocityY < 0) {

            smoothScrollTo(scrollY, scrollAmount - 531, 1579)

            scrollAmount -= 531

        } else {

            smoothScrollTo(scrollY, scrollAmount + 531, 1579)

            scrollAmount += 531

        }

    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean) : Boolean {

        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionEvent: MotionEvent) : Boolean {

        return when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {

                isScrollable && super.onTouchEvent(motionEvent)

            }
            MotionEvent.ACTION_MOVE -> {

                isScrollable && super.onTouchEvent(motionEvent)

            }
            else -> {
                super.onTouchEvent(motionEvent)
            }
        }
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent?) : Boolean {

        return isScrollable && super.onInterceptTouchEvent(motionEvent)
    }

}