/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/13/21, 2:01 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Animations

import android.widget.TextView
import kotlinx.coroutines.*

fun TextView.startTypingAnimation(inputText: String) = CoroutineScope(SupervisorJob() + Dispatchers.Main).async {

    inputText.forEach {

        this@startTypingAnimation.append(it.toString())

        delay(151)

    }

}