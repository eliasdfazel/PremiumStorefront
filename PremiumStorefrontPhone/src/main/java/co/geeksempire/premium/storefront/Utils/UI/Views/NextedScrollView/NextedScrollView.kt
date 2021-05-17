/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/17/21, 12:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.NextedScrollView

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger


class NextedScrollView(context: Context, attributesSet: AttributeSet) : NestedScrollView(context, attributesSet) {

    private var scrollAmount = 0

    private var distanceToScroll = dpToInteger(context, 199) * 3

    override fun fling(velocityY: Int) {

        if (velocityY < 0) {

            smoothScrollTo(scrollY, scrollAmount - distanceToScroll, 1777)

            scrollAmount -= distanceToScroll

        } else {

            smoothScrollTo(scrollY, scrollAmount + distanceToScroll, 1777)

            scrollAmount += distanceToScroll

        }

    }

    fun distanceToScroll(newDistanceToScroll: Int) {

        distanceToScroll = newDistanceToScroll
    }

}