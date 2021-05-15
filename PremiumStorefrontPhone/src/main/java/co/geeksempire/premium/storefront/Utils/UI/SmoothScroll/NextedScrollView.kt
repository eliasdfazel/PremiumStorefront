/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/15/21, 10:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.SmoothScroll

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import co.geeksempire.premium.storefront.R


class NextedScrollView(context: Context, attributesSet: AttributeSet) : NestedScrollView(context, attributesSet) {

    var attributeSet: TypedArray = context.obtainStyledAttributes(attributesSet, R.styleable.GlowButton)

//    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
//        val rv = target as RecyclerView
//
//        if (dy < 0 && isRvScrolledToTop(rv) || dy > 0 && !isNsvScrolledToBottom(this)) {
//
//            scrollBy(0, dy)
//            consumed[1] = dy
//
//            return
//        }
//
//        super.onNestedPreScroll(target, dx, dy, consumed)
//    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {

        val yVelocity = (velocityY/5).toInt()

//        val rv = target as RecyclerView
//
//        if (velocityY < 0 && isRvScrolledToTop(rv) || velocityY > 0 && !isNsvScrolledToBottom(this)) {
//
//            fling(velocityY.toInt() / 5)
//
//            return true
//        }

//        smoothScrollTo(0, 150, 975)

        return true//super.onNestedPreFling(target, velocityX, yVelocity.toFloat())
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        val yVelocity = (velocityY/5).toInt()

//        fling(yVelocity)

        smoothScrollTo(0, 50, 975)

        return true//super.onNestedFling(target, velocityX, yVelocity.toFloat(), consumed)
    }

}