/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/19/21, 7:30 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.balloon.optionsmenu.library

import android.graphics.Typeface
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.geeksempire.balloon.optionsmenu.library.Utils.displayX
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger
import net.geeksempire.balloon.optionsmenu.library.databinding.BalloonOptionsMenuLayoutBinding

interface BalloonItemsAction {
    fun onBalloonItemClickListener(balloonOptionsMenu: BalloonOptionsMenu, balloonOptionsRootView: View, itemView: View, itemTextView: TextView)
}

data class TitleTextViewCustomization(var textSize: Float, var textColor: Int, var textShadowColor: Int, var textFont: Typeface)
data class ItemTextViewCustomization(var textSize: Float, var textColor: Int, var textShadowColor: Int, var textFont: Typeface)

class BalloonOptionsMenu (private val context: AppCompatActivity,
                          private val rootView: ViewGroup) {

    var balloonItemsAction: BalloonItemsAction? = null

    private val balloonOptionsMenuLayoutBinding = BalloonOptionsMenuLayoutBinding.inflate(context.layoutInflater)
    private val allBalloonOptionsMenuItemsView = balloonOptionsMenuLayoutBinding.allBalloonOptionsMenuItemsView

    private val positionXY = IntArray(2)

    var viewX = 0
    var viewY = 0

    var balloonOptionsAdded = false

    fun initializeBalloonPosition(anchorView: View,
                                  verticalOffset: Int = 0,
                                  horizontalOffset: Int = 0,
                                  startAnimationId: Int = android.R.anim.fade_in) : BalloonOptionsMenu {

        removeBalloonOption()

        anchorView.getLocationInWindow(positionXY)

        viewX = positionXY[0]
        viewY = positionXY[1]
        Log.d(this@BalloonOptionsMenu.javaClass.simpleName, "TouchX: ${viewX} | TouchY: ${viewY}")

        if (balloonOptionsAdded) {

            balloonOptionsAdded = false

            rootView.removeView(balloonOptionsMenuLayoutBinding.root)

        }

        rootView.addView(balloonOptionsMenuLayoutBinding.root)
        balloonOptionsMenuLayoutBinding.root.startAnimation(AnimationUtils.loadAnimation(context, startAnimationId))

        balloonOptionsMenuLayoutBinding.root.x = (displayX(context)).toFloat() - dpToInteger(context, 151) - horizontalOffset
        balloonOptionsMenuLayoutBinding.root.y = viewY.toFloat() - verticalOffset

        balloonOptionsAdded = true

        balloonOptionsMenuLayoutBinding.root.setOnFocusChangeListener { view, hasFocus ->


        }

        return this@BalloonOptionsMenu
    }

    fun setupOptionsItems(menuTitle: String?,
                          titlesOfItems: ArrayList<String>,
                          titleTextViewCustomization: TitleTextViewCustomization? = null,
                          itemTextViewCustomization: ItemTextViewCustomization? = null) {

        balloonOptionsMenuLayoutBinding.menuTitleTextView.text = menuTitle

        titleTextViewCustomization?.let {

            balloonOptionsMenuLayoutBinding.menuTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, it.textSize)
            balloonOptionsMenuLayoutBinding.menuTitleTextView.setTextColor(it.textColor)
            balloonOptionsMenuLayoutBinding.menuTitleTextView.setShadowLayer(balloonOptionsMenuLayoutBinding.menuTitleTextView.shadowRadius, balloonOptionsMenuLayoutBinding.menuTitleTextView.shadowDx, balloonOptionsMenuLayoutBinding.menuTitleTextView.shadowDy, it.textShadowColor)
            balloonOptionsMenuLayoutBinding.menuTitleTextView.typeface = it.textFont

        }

        balloonOptionsMenuLayoutBinding.menuTitleTextView.setOnClickListener {

            removeBalloonOption()

        }

        titlesOfItems.forEach { content ->

            val itemLayout = LayoutInflater.from(context).inflate(R.layout.balloon_option_item, null)
            val balloonOptionItemTextView = itemLayout.findViewById<TextView>(R.id.balloonOptionItemTextView)

            balloonOptionItemTextView.text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
            balloonOptionItemTextView.tag = content

            itemTextViewCustomization?.let {

                balloonOptionItemTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, it.textSize)
                balloonOptionItemTextView.setTextColor(it.textColor)
                balloonOptionItemTextView.setShadowLayer(balloonOptionItemTextView.shadowRadius, balloonOptionItemTextView.shadowDx, balloonOptionItemTextView.shadowDy, it.textShadowColor)
                balloonOptionItemTextView.typeface = it.textFont

            }

            itemLayout.setOnClickListener { view ->

                balloonItemsAction?.onBalloonItemClickListener(this@BalloonOptionsMenu, balloonOptionsMenuLayoutBinding.root, view, balloonOptionItemTextView)

            }

            try {

                allBalloonOptionsMenuItemsView.addView(itemLayout)

            } catch (e: Exception) {
                e.printStackTrace()

            }

        }

        titlesOfItems.clear()

    }

    fun setupActionListener(balloonItemsAction: BalloonItemsAction) {

        this@BalloonOptionsMenu.balloonItemsAction = balloonItemsAction

    }

    fun removeBalloonOption() {

        try {
            if (balloonOptionsAdded) {

                balloonOptionsAdded = false

                balloonOptionsMenuLayoutBinding.allBalloonOptionsMenuItemsView.removeAllViews()

                rootView.removeView(balloonOptionsMenuLayoutBinding.root)

            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

}