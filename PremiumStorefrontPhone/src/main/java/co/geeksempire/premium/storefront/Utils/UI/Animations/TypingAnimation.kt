/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/14/21, 8:18 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Animations

import android.widget.TextView
import kotlinx.coroutines.*

fun TextView.startTypingAnimation(inputText: String) = CoroutineScope(SupervisorJob() + Dispatchers.Main).async {

    this@startTypingAnimation.text = null

    inputText.forEach {

        this@startTypingAnimation.append(it.toString())

        delay(151)

    }

}