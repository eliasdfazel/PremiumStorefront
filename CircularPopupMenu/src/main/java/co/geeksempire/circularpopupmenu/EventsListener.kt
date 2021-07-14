/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/14/21, 12:56 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.circularpopupmenu

internal class EventsListener {

    var onMenuOpenAnimationStart: (() -> Unit)? = null
    var onMenuOpenAnimationEnd: (() -> Unit)? = null
    var onMenuCloseAnimationStart: (() -> Unit)? = null
    var onMenuCloseAnimationEnd: (() -> Unit)? = null
    var onButtonClickAnimationStart: ((buttonIndex: Int) -> Unit)? = null
    var onButtonClickAnimationEnd: ((buttonIndex: Int) -> Unit)? = null

}