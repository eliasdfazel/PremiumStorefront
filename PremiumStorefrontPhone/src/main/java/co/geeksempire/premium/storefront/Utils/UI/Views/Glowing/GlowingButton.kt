/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/27/21 10:01 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Views.Glowing

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.R

class GlowingButton(context: Context, attributesSet: AttributeSet) : AppCompatButton(context, attributesSet), View.OnTouchListener  {

    var attributeSet: TypedArray = context.obtainStyledAttributes(attributesSet, R.styleable.GlowButton)

    private var glowColor: Int = attributeSet.getColor(R.styleable.GlowButton_glowColor, Color.TRANSPARENT)

    private var aBackgroundColor: Int = attributeSet.getColor(R.styleable.GlowButton_backgroundColor, Color.TRANSPARENT)

    private var unpressedGlowSize: Int = attributeSet.getDimensionPixelSize(R.styleable.GlowButton_unpressedGlowSize, R.dimen.default_unpressed_glow_size)

    private var pressedGlowSize: Int = attributeSet.getDimensionPixelSize(R.styleable.GlowButton_pressedGlowSize, R.dimen.default_pressed_glow_size)

    private var shadowX: Float = attributeSet.getDimension(R.styleable.GlowButton_shadowX, 0f)
    private  var shadowY: Float = attributeSet.getDimension(R.styleable.GlowButton_shadowY, 0f)

    private var topLeftCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusTopLeft, 0f)
    private var topRightCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusTopRight, 0f)
    private var bottomLeftCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusBottomLeft, 0f)
    private var bottomRightCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusBottomRight, 0f)

    init {

        setOnTouchListener(this@GlowingButton)

        val resources = resources

        aBackgroundColor = resources!!.getColor(R.color.default_color_bright)
        glowColor = resources!!.getColor(R.color.default_color_game)

        unpressedGlowSize = resources!!.getDimensionPixelSize(R.dimen.default_unpressed_glow_size)
        pressedGlowSize = resources!!.getDimensionPixelSize(R.dimen.default_pressed_glow_size)



        glowColor = attributeSet.getColor(R.styleable.GlowButton_glowColor, Color.TRANSPARENT)

        aBackgroundColor = attributeSet.getColor(R.styleable.GlowButton_backgroundColor, Color.TRANSPARENT)

        unpressedGlowSize = attributeSet.getDimensionPixelSize(R.styleable.GlowButton_unpressedGlowSize, R.dimen.default_unpressed_glow_size)

        pressedGlowSize = attributeSet.getDimensionPixelSize(R.styleable.GlowButton_pressedGlowSize, R.dimen.default_pressed_glow_size)

        shadowX = attributeSet.getDimension(R.styleable.GlowButton_shadowX, 0f)
        var shadowY: Float = attributeSet.getDimension(R.styleable.GlowButton_shadowY, 0f)

        topLeftCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusTopLeft, 0f)
        topRightCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusTopRight, 0f)
        bottomLeftCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusBottomLeft, 0f)
        bottomRightCorner = attributeSet.getDimension(R.styleable.GlowButton_cornerRadiusBottomRight, 0f)

        attributeSet.recycle()

    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {

        when (motionEvent?.action) {
            MotionEvent.ACTION_DOWN -> {

                background = getBackgroundWithGlow(this@GlowingButton, aBackgroundColor, glowColor, unpressedGlowSize, pressedGlowSize)

            }
            MotionEvent.ACTION_UP -> {

                background = getBackgroundWithGlow(this@GlowingButton, aBackgroundColor, glowColor, unpressedGlowSize, unpressedGlowSize)

            }
            MotionEvent.ACTION_CANCEL -> {

                background = getBackgroundWithGlow(this@GlowingButton, aBackgroundColor, glowColor, unpressedGlowSize, unpressedGlowSize)

            }
        }

        return false
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        updateButtonGlow()

    }

    override fun setBackgroundColor(backgroundColor: Int) {

        aBackgroundColor = backgroundColor

        updateButtonGlow()

    }

    /*
    *
    *
    * Functions
    *
    *
    */
    private fun updateButtonGlow() {

        background = getBackgroundWithGlow(this, aBackgroundColor, glowColor, unpressedGlowSize, unpressedGlowSize)

    }

    fun setGlowColor(glowColor: Int) {

        this.glowColor = glowColor

        updateButtonGlow()
    }

    fun setUnpressedGlowSize(unpressedGlowSize: Int) {

        this.unpressedGlowSize = unpressedGlowSize

        updateButtonGlow()
    }

    fun setPressedGlowSize(pressedGlowSize: Int) {

        this.pressedGlowSize = pressedGlowSize

        updateButtonGlow()
    }

    fun setCornerRadius(topLeftCorner: Float, topRightCorner: Float, bottomLeftCorner: Float, bottomRightCorner: Float) {

        this.topLeftCorner = topLeftCorner
        this.topRightCorner = topRightCorner

        this.topLeftCorner = bottomLeftCorner
        this.topRightCorner = bottomRightCorner

        updateButtonGlow()
    }

    fun getBackgroundWithGlow(view: View, backgroundColor: Int,
                              glowColor: Int,
                              unpressedGlowSize: Int,
                              pressedGlowSize: Int): Drawable {

        val outerRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

        outerRadius[0] = topLeftCorner
        outerRadius[1] = topLeftCorner

        outerRadius[2] = topRightCorner
        outerRadius[3] = topRightCorner

        outerRadius[4] = bottomRightCorner
        outerRadius[5] = bottomRightCorner

        outerRadius[6] = bottomLeftCorner
        outerRadius[7] = bottomLeftCorner

        val shapeDrawablePadding = Rect()

        val shapeDrawable = ShapeDrawable()

        shapeDrawable.setPadding(shapeDrawablePadding)
        shapeDrawable.paint.color = backgroundColor
        shapeDrawable.paint.setShadowLayer(pressedGlowSize.toFloat(), shadowX, shadowY, glowColor)

        /* *** */


        /* *** */

        view.setLayerType(LAYER_TYPE_SOFTWARE, shapeDrawable.paint)

        shapeDrawable.shape = RoundRectShape(outerRadius, null, null)
        shapeDrawable.setPadding(11, 11, 11, 11)

        val layerDrawable = LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        layerDrawable.setLayerInset(0, unpressedGlowSize, unpressedGlowSize, unpressedGlowSize, unpressedGlowSize)

        return layerDrawable
    }

}