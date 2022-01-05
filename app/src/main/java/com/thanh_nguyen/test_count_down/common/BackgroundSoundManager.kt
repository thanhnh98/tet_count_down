/*
 * Created by Thanh Nguyen on 11/24/21, 11:21 AM
 */

package com.thanh_nguyen.test_count_down.common

import android.content.Context
import android.media.MediaPlayer
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.R

class BackgroundSoundManager(context: Context) {

    companion object {
        val DEFAULT_MUSIC = MediaPlayer.create(App.getInstance(), R.raw.hpny).apply {
            isLooping = true
        }
    }
    private var backgroundMusic = DEFAULT_MUSIC

    private val fireworkSound by lazy {
        MediaPlayer.create(App.getInstance(), R.raw.fireworks).apply {
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
        if (backgroundMusic != mediaPlayer)
            backgroundMusic = mediaPlayer.apply {
                isLooping = true
            }
    }

    fun restartBackgroundMusic(){
        backgroundMusic.stop()
        backgroundMusic.prepare()
        backgroundMusic.start()
    }
}