package com.thanh_nguyen.test_count_down.utils

import android.animation.Animator
import android.view.View

fun View.fadeInAppearance(
                        alpha: Float = 1f,
                        scaleX: Float = 1f,
                        scaleY: Float = 1f,
                        callback: (() -> Unit)? = null
){
    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate()
        .setDuration(500L)
        .setListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                this@fadeInAppearance.alpha = alpha
                callback?.invoke()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
        .alpha(alpha)
        .scaleX(scaleX)
        .scaleY(scaleY)
        .start()
}