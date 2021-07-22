/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/22/21, 9:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Gesture

sealed class GestureConstants {
    class SwipeHorizontal(var horizontalDirection: Int) : GestureConstants()
    class SwipeVertical(var verticalDirection: Int) : GestureConstants()
}

class GestureListenerConstants {

    companion object {
        const val SWIPE_UP = 1
        const val SWIPE_DOWN = 2
        const val SWIPE_LEFT = 3
        const val SWIPE_RIGHT = 4

        const val MODE_SOLID = 1
        const val MODE_DYNAMIC = 2

        const val ACTION_FAKE = -666
    }
}