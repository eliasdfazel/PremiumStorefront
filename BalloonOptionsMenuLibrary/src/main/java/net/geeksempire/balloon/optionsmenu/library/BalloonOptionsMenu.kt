/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/4/21 12:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.balloon.optionsmenu.library

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.geeksempire.balloon.optionsmenu.library.Utils.displayX
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

interface BalloonItemsAction {
    fun onBalloonItemClickListener(balloonOptionsMenu: BalloonOptionsMenu, balloonOptionsRootView: View, itemView: View)
}

class BalloonOptionsMenu (private val context: AppCompatActivity,
                          private val rootView: ViewGroup,
                          private val balloonItemsAction: BalloonItemsAction) {

    private val balloonOptionsMenuLayoutBinding = LayoutInflater.from(context).inflate(R.layout.balloon_options_menu_layout, null)
    private val allBalloonOptionsMenuItemsView = balloonOptionsMenuLayoutBinding.findViewById<LinearLayout>(R.id.allBalloonOptionsMenuItemsView)

    private val positionXY = IntArray(2)

    var viewX = 0
    var viewY = 0

    var balloonOptionsAdded = false

    fun initializeBalloonPosition(anchorView: View,
                                  startAnimationId: Int = android.R.anim.fade_in) : BalloonOptionsMenu {

        anchorView.getLocationInWindow(positionXY)

        viewX = positionXY[0]
        viewY = positionXY[1]
        Log.d(this@BalloonOptionsMenu.javaClass.simpleName, "TouchX: ${viewX} | TouchY: ${viewY}")

        if (balloonOptionsAdded) {

            balloonOptionsAdded = false

            rootView.removeView(balloonOptionsMenuLayoutBinding)

        }

        rootView.addView(balloonOptionsMenuLayoutBinding)
        balloonOptionsMenuLayoutBinding.startAnimation(AnimationUtils.loadAnimation(context, startAnimationId))

        balloonOptionsMenuLayoutBinding.x = (displayX(context) / 2).toFloat() - dpToInteger(context, 75)
        balloonOptionsMenuLayoutBinding.y = viewY.toFloat()

        balloonOptionsAdded = true

        balloonOptionsMenuLayoutBinding.setOnFocusChangeListener { view, hasFocus ->


        }

        return this@BalloonOptionsMenu
    }

    fun setupOptionsItems(titlesOfItems: ArrayList<String>) {

        titlesOfItems.forEach {

            val itemLayout = LayoutInflater.from(context).inflate(R.layout.balloon_option_item, null)
            val balloonOptionItemTextView = itemLayout.findViewById<TextView>(R.id.balloonOptionItemTextView)

            balloonOptionItemTextView.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            balloonOptionItemTextView.tag = it

            itemLayout.setOnClickListener { view ->

                balloonItemsAction.onBalloonItemClickListener(this@BalloonOptionsMenu, balloonOptionsMenuLayoutBinding, view)

            }

            try {

                allBalloonOptionsMenuItemsView.addView(itemLayout)

            } catch (e: Exception) {

            }

        }

    }

    fun removeBalloonOption() {

        if (balloonOptionsAdded) {

            balloonOptionsAdded = false

            rootView.removeView(balloonOptionsMenuLayoutBinding)

        }

    }

}