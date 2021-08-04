/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/4/21, 5:30 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.ControlledScrollView

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

fun snappedItemPosition(recyclerView: RecyclerView, snapHelper: SnapHelper) : Int {

    val layoutManager = recyclerView.layoutManager
    val snapView = snapHelper.findSnapView(layoutManager)

    return snapView?.let { layoutManager?.getPosition(it) } ?:0
}