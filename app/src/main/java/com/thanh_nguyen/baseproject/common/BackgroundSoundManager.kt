/*
 * Created by Thanh Nguyen on 11/24/21, 11:21 AM
 */

package com.thanh_nguyen.baseproject.common

import android.content.Context
import android.media.MediaPlayer
import com.thanh_nguyen.baseproject.R

class BackgroundSoundManager(context: Context) {
    private val mediaPlayer by lazy {
        MediaPlayer.create(context, R.raw.hpny).apply {
            isLooping = true
        }
    }
    private val fireworkSound by lazy {
        MediaPlayer.create(context, R.raw.fireworks).apply {
            isLooping = true
        }
    }

    fun playFireworkSound(){
        if (!fireworkSound.isPlaying) {
            fireworkSound.start()
        }
    }

    fun playBackgroundSound(){
        if(!mediaPlayer.isPlaying){
            mediaPlayer.start()
        }
    }

    fun pauseBackgroundSound(){
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    fun pauseFireworkSound(){
        if (fireworkSound.isPlaying)
            fireworkSound.pause()
    }

    fun stopBackgroundSound(){
        //mediaPlayer.stop()
    }

    fun stopFireworkSound(){
        //fireworkSound.stop()
    }

}