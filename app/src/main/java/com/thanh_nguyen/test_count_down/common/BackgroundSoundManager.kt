/*
 * Created by Thanh Nguyen on 11/24/21, 11:21 AM
 */

package com.thanh_nguyen.test_count_down.common

import android.content.Context
import android.media.MediaPlayer
import com.thanh_nguyen.test_count_down.R

class BackgroundSoundManager(context: Context) {
    private var backgroundMusic = MediaPlayer.create(context, R.raw.hpny).apply {
        isLooping = true
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
        if(!backgroundMusic.isPlaying){
            backgroundMusic.start()
        }
    }

    fun pauseBackgroundSound(){
        if (backgroundMusic.isPlaying)
            backgroundMusic.pause()
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

    fun updateBackgroundMusic(mediaPlayer: MediaPlayer){
        backgroundMusic.stop()
        backgroundMusic = mediaPlayer.apply {
            isLooping = true
        }
        backgroundMusic.prepare()
        backgroundMusic.start()
    }
}