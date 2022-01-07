/*
 * Created by Thanh Nguyen on 11/24/21, 11:21 AM
 */

package com.thanh_nguyen.test_count_down.common

import android.media.MediaPlayer
import android.net.Uri
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.utils.cmn
import com.thanh_nguyen.test_count_down.utils.createMedia
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class SoundManager {
    companion object {
        val DEFAULT_MUSIC = MediaPlayer.create(App.getInstance(), R.raw.hpny).apply {
            isLooping = true
        }
    }

    private var _musicState: MutableSharedFlow<MusicState> = MutableSharedFlow()
    val musicState: SharedFlow<MusicState> = _musicState

    private var backgroundMusic = DEFAULT_MUSIC
    private var currentBackgroundTrackUri: Uri? = null

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

    fun updateBackgroundMusic(uri: Uri, requestPlay: Boolean = false){
        if (this.currentBackgroundTrackUri != uri){
            this.currentBackgroundTrackUri = uri
            backgroundMusic.stop()
            backgroundMusic.reset()
            backgroundMusic.release()
            backgroundMusic = createMedia(uri).apply {
                isLooping = true
                prepare()
            }
        }
        if (requestPlay)
           playBackgroundSound()
    }

    fun restartBackgroundMusic(){
        backgroundMusic.stop()
        backgroundMusic.release()
        backgroundMusic.start()
    }

    fun getBackgroundMusic() = backgroundMusic

    fun notifyChangeState(state: MusicState){
        _musicState.tryEmit(state)
    }
}

enum class MusicState{
    PLAY,
    PAUSE
}