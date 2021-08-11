/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/11/21, 8:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.youtubeplayer.ui.menu

import android.view.View
import androidx.annotation.DrawableRes

data class MenuItem @JvmOverloads constructor(val text: String, @DrawableRes val icon: Int? = null, val onClickListener: View.OnClickListener)