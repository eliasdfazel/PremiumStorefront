/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/16/21, 12:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.NextedScrollView

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import co.geeksempire.premium.storefront.R


class NextedScrollView(context: Context, attributesSet: AttributeSet) : NestedScrollView(context, attributesSet) {

    var attributeSet: TypedArray = context.obtainStyledAttributes(attributesSet, R.styleable.NextedScrollView)

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {

        val yVelocity = (velocityY/5).toInt()

        fling(yVelocity)

        smoothScrollTo(0, 50, 975)

        return super.onNestedFling(target, velocityX, yVelocity.toFloat(), consumed)
    }

}