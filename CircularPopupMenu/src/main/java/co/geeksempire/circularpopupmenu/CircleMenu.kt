/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/15/21, 6:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.circularpopupmenu

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CircleMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs) {

    val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.CircleMenu)

    val isOpened: Boolean
        get() = menuLayout.isOpened

    private val menuLayout: CircleMenuLayout

    var backgroundColorsOfItem: List<Int>
    var iconsOfItems: List<Int>

    init {
        isClickable = true

        attributeSet.apply {
            val circleStartAngle = getInteger(
                    R.styleable.CircleMenu_startAngle,
                    resources.getInteger(R.integer.circle_menu_start_angle)
            )
            val circleMaxAngle = getInteger(
                    R.styleable.CircleMenu_maxAngle,
                    resources.getInteger(R.integer.circle_menu_max_angle)
            )
            val distance = getDimension(
                    R.styleable.CircleMenu_distance,
                    resources.getDimension(R.dimen.circle_menu_distance)
            ).toInt()
            val openOnStart = getBoolean(R.styleable.CircleMenu_openOnStart, false)

            val centerButtonColorDefault = ContextCompat.getColor(context, R.color.circle_menu_center_button_color)
            val centerButtonColor = getColor(R.styleable.CircleMenu_centerButtonColor, centerButtonColorDefault)

            val centerButtonIconColorDefault = ContextCompat.getColor(context, R.color.circle_menu_center_button_icon_color)
            val centerButtonIconColor = getColor(R.styleable.CircleMenu_centerButtonIconColor, centerButtonIconColorDefault)

            val menuIconType = MenuIconType.values()[getInt(R.styleable.CircleMenu_menuIcon, 0)]

            val iconsColorDef = ContextCompat.getColor(context, R.color.circle_menu_button_icon_color)
            val buttonIconsColor = getColor(R.styleable.CircleMenu_iconsColor, iconsColorDef)

            val iconArrayId: Int = getResourceId(R.styleable.CircleMenu_buttonIcons, 0)
            val colorArrayId: Int = getResourceId(R.styleable.CircleMenu_buttonColors, 0)

            val showSelectAnimation: Boolean = getBoolean(R.styleable.CircleMenu_showSelectAnimation, true)

            backgroundColorsOfItem = resources.getIntArray(colorArrayId).asList()

            iconsOfItems = resources.obtainTypedArray(iconArrayId).let { iconsIds ->
                (0 until iconsIds.length()).map {
                    iconsIds.getResourceId(it, -1)
                }
            }

            if (backgroundColorsOfItem.isNullOrEmpty() || iconsOfItems.isNullOrEmpty()) {
                throw IllegalArgumentException("Colors and icons array must not be empty")
            }

            if (backgroundColorsOfItem.size != iconsOfItems.size) {
                throw IllegalArgumentException("Colors array size must be equal to the icons array")
            }

            menuLayout = CircleMenuLayout(
                    context = context,
                    centerButtonColor = centerButtonColor,
                    centerButtonIconColor = centerButtonIconColor,
                    menuIconType = menuIconType,
                    buttonIconsColor = buttonIconsColor,
                    distance = distance,
                    circleMaxAngle = circleMaxAngle,
                    circleStartAngle = circleStartAngle,
                    showSelectAnimation = showSelectAnimation,
                    openOnStart = openOnStart,
                    colors = backgroundColorsOfItem,
                    icons = iconsOfItems
            )

            initMenuButton(menuIconType, centerButtonColor, centerButtonIconColor)
            initMenuLayout()
        }.recycle()
    }

    fun setColorsOfItem(listOfColors: List<Int>?) {

        backgroundColorsOfItem = if (!listOfColors.isNullOrEmpty()) {
            listOfColors
        } else {
            resources.getIntArray(attributeSet.getResourceId(R.styleable.CircleMenu_buttonColors, 0)).asList()
        }
        menuLayout.colors = backgroundColorsOfItem
        invalidate()
    }

    fun setIconsOfItem(listOfIcons: List<Int>?) {

        iconsOfItems = if (!listOfIcons.isNullOrEmpty()) {
            listOfIcons
        } else {
            resources.getIntArray(attributeSet.getResourceId(R.styleable.CircleMenu_buttonIcons, 0)).asList()
        }
        menuLayout.icons = iconsOfItems
        invalidate()
    }

    private fun initMenuButton(
            iconType: MenuIconType,
            buttonColor: Int,
            iconColor: Int
    ) {
        val iconResId = if (iconType == MenuIconType.PLUS) {
            R.drawable.ic_plus
        } else {
            R.drawable.ic_menu
        }
        val icon = ContextCompat.getDrawable(context, iconResId)!!
        icon.setTintCompat(iconColor)
        setImageDrawable(icon)
        backgroundTintList = ColorStateList.valueOf(buttonColor)
    }

    private fun initMenuLayout() {
        val activity = context as Activity
        val decor = activity.window.decorView as ViewGroup
        val menuButton = this

        onLaidOut {
            val menuButtonLocation = IntArray(2).let {
                menuButton.getLocationOnScreen(it)
                Point(it.first(), it.last())
            }

            val bounds = Rect(menuButtonLocation.x, menuButtonLocation.y,
                    menuButtonLocation.x + menuButton.width,
                    menuButtonLocation.y + menuButton.height
            )

            val parentLocation = IntArray(2).let {
                decor.getLocationOnScreen(it)
                Point(it.first(), it.last())
            }
            bounds.offset(-parentLocation.x, -parentLocation.y)

            val menuLayoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            )
            decor.addView(menuLayout, menuLayoutParams)
            menuLayout.post {
                val width = menuLayout.width
                val height = menuLayout.height
                decor.removeView(menuLayout)
                val params = FrameLayout.LayoutParams(
                        width,
                        height
                )
                params.leftMargin = bounds.centerX() - (menuLayout.width / 2)
                params.topMargin = bounds.centerY() - (menuLayout.height / 2)
                decor.addView(menuLayout, params)
            }
        }



        super.setOnClickListener {
            showMenu()
        }
    }

    private fun showMenu() {
        menuLayout.visibility = View.VISIBLE
        menuLayout.open(true)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        //empty
    }

    fun setOnItemClickListener(listener: (buttonIndex: Int) -> Unit) {
        this.menuLayout.setOnItemClickListener(listener)
    }

    fun setOnItemLongClickListener(listener: (buttonIndex: Int) -> Unit) {
        this.menuLayout.setOnItemLongClickListener(listener)
    }

    fun toggle() {
        this.menuLayout.toggle()
    }

    fun open(animate: Boolean = true) {
        this.menuLayout.open(animate)
    }

    fun close(animate: Boolean) {
        this.menuLayout.open(animate)
    }

    fun onMenuOpenAnimationStart(listener: () -> Unit) {
        this.menuLayout.onMenuOpenAnimationStart(listener)
    }

    fun onMenuOpenAnimationEnd(listener: () -> Unit) {
        this.menuLayout.onMenuOpenAnimationEnd(listener)
    }

    fun onMenuCloseAnimationStart(listener: () -> Unit) {
        this.menuLayout.onMenuCloseAnimationStart(listener)
    }

    fun onMenuCloseAnimationEnd(listener: () -> Unit) {
        this.menuLayout.onMenuCloseAnimationEnd(listener)
    }

    fun onButtonClickAnimationStart(listener: (buttonIndex: Int) -> Unit) {
        this.menuLayout.onButtonClickAnimationStart(listener)
    }

    fun onButtonClickAnimationEnd(listener: (buttonIndex: Int) -> Unit) {
        this.menuLayout.onButtonClickAnimationEnd(listener)
    }

    internal enum class MenuIconType(val code: String) {
        HAMBURGER("hamburger"), PLUS("plus")
    }

}