/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/13/21, 9:37 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.ControlledScrollView

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToPixel
import kotlin.math.roundToInt

class HorizontalSpacingItemDecoration(context: Context, space: Int = 19) : RecyclerView.ItemDecoration() {

    private val spaceInDp = dpToPixel(context, space.toFloat())

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.right = spaceInDp.roundToInt()

    }
}