/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/17/21, 12:37 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.NextedScrollView

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import co.geeksempire.premium.storefront.R


class NextedScrollView(context: Context, attributesSet: AttributeSet) : NestedScrollView(context, attributesSet) {

    var attributeSet: TypedArray = context.obtainStyledAttributes(attributesSet, R.styleable.NextedScrollView)

    val isScrollable: Boolean = true

    var scrollAmount = 0

    override fun fling(velocityY: Int) {
        super.fling(velocityY/3)

//        if (velocityY < 0) {
//
//            smoothScrollTo(scrollY, scrollAmount - 531, 1579)
//
//            scrollAmount -= 531
//
//        } else {
//
//            smoothScrollTo(scrollY, scrollAmount + 531, 1579)
//
//            scrollAmount += 531
//
//        }

    }

}