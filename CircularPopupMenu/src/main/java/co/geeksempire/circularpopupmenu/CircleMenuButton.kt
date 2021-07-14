/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/14/21, 2:49 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.circularpopupmenu

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CircleMenuButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs) {

    internal var iconResId: Int = -1
    private var iconColor: Int = Color.WHITE

    init {
        isClickable = true
    }

    internal fun setIconNoTint(@DrawableRes iconResId: Int) {
        this.iconResId = iconResId

        val icon = ContextCompat.getDrawable(context, iconResId)!!
//        icon.setTintCompat(Color.TRANSPARENT)
        icon.setTintMode(PorterDuff.Mode.MULTIPLY)
        setImageDrawable(icon)
    }

    internal fun setIcon(@DrawableRes iconResId: Int) {
        this.iconResId = iconResId

        val icon = ContextCompat.getDrawable(context, iconResId)!!
        icon.setTintCompat(iconColor)
        setImageDrawable(icon)
    }

    fun setIconColor(buttonIconsColor: Int) {
        this.iconColor = buttonIconsColor
    }

    internal fun setColor(color: Int) {
        backgroundTintList = ColorStateList.valueOf(color)
    }

}