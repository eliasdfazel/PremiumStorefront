/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/19/21, 2:22 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Utils.UI.Animations

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class ShadowAnimation {

    fun textShadowValueAnimatorLoop(view: MaterialButton,
                                    startValue: Float, endValue: Float,
                                    startDuration: Int = 777, endDuration: Int = 333,
                                    shadowColor: Int, shadowX: Float, shadowY: Float,
                                    numberOfLoop: Int = 7) {

        var loopCounter = 0

        val glowDownAnimation = ValueAnimator.ofFloat(startValue, endValue)
        glowDownAnimation.startDelay = startDuration.toLong()
        glowDownAnimation.duration = startDuration.toLong()
        glowDownAnimation.addUpdateListener { animator ->

            view.setShadowLayer((animator.animatedValue as Float), shadowX, shadowY, shadowColor)

        }
        glowDownAnimation.interpolator = OvershootInterpolator()
        glowDownAnimation.start()
        glowDownAnimation.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val glowUpAnimation = ValueAnimator.ofFloat(endValue, startValue)
                glowUpAnimation.duration = endDuration.toLong()
                glowUpAnimation.addUpdateListener { animator ->

                    view.setShadowLayer((animator.animatedValue as Float), shadowX, shadowY,  shadowColor)

                }
                glowUpAnimation.interpolator = OvershootInterpolator()
                glowUpAnimation.start()
                glowUpAnimation.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {

                        if (loopCounter != numberOfLoop) {

                            textShadowValueAnimatorLoop(view,
                                startValue, endValue,
                                startDuration, endDuration,
                                shadowColor, shadowX, shadowY)

                        }

                        loopCounter++

                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }
                })
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }

    fun textShadowValueAnimatorLoop(view: TextView,
                                    startValue: Float, endValue: Float,
                                    startDuration: Int = 777, endDuration: Int = 333,
                                    shadowColor: Int, shadowX: Float, shadowY: Float,
                                    numberOfLoop: Int = 7) {

        var loopCounter = 0

        val glowDownAnimation = ValueAnimator.ofFloat(startValue, endValue)
        glowDownAnimation.startDelay = startDuration.toLong()
        glowDownAnimation.duration = startDuration.toLong()
        glowDownAnimation.addUpdateListener { animator ->

            view.setShadowLayer((animator.animatedValue as Float), shadowX, shadowY, shadowColor)

        }
        glowDownAnimation.interpolator = OvershootInterpolator()
        glowDownAnimation.start()
        glowDownAnimation.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val glowUpAnimation = ValueAnimator.ofFloat(endValue, startValue)
                glowUpAnimation.duration = endDuration.toLong()
                glowUpAnimation.addUpdateListener { animator ->

                    view.setShadowLayer((animator.animatedValue as Float), shadowX, shadowY,  shadowColor)

                }
                glowUpAnimation.interpolator = OvershootInterpolator()
                glowUpAnimation.start()
                glowUpAnimation.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {

                        if (loopCounter != numberOfLoop) {

                            textShadowValueAnimatorLoop(view,
                                startValue, endValue,
                                startDuration, endDuration,
                                shadowColor, shadowX, shadowY)

                        }

                        loopCounter++

                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }
                })
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }

}