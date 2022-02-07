/*
 * Created by Thanh Nguyen on 11/24/21, 11:21 AM
 */

package com.thanh_nguyen.test_count_down.common

import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel
import com.thanh_nguyen.test_count_down.utils.createMedia

class SoundManager {
    private val DEFAULT_MUSIC = MediaPlayer.create(App.getInstance(), R.raw.background_music).apply {
        isLooping = true
    }

    private var _musicState: MutableLiveData<MusicState> = MutableLiveData()
    val musicStateChanged: LiveData<MusicState> = _musicState

    private var backgroundMusic = DEFAULT_MUSIC
    private var currentBackgroundTrackUri: Uri? = null

    private val fireworkSound by lazy {
        MediaPlayer.create(App.getInstance(), R.raw.fireworks).apply {
            isLooping = true
        }
    }

    fun initBackgroundMusic(mediaPlayer: MediaPlayer){
        stopBackgroundSound()
        backgroundMusic = mediaPlayer.apply {
            isLooping = true
            prepare()
        }
    }

    fun playFireworkSound(){
        if (!fireworkSound.isPlaying) {
            fireworkSound.start()
        }
    }

    fun playBackgroundSound(){
        try {
            backgroundMusic.start()
        }
        catch (e: IllegalStateException){
            backgroundMusic.reset()
            backgroundMusic.release()
            backgroundMusic.start()
        }
    }

    fun pauseBackgroundSound(){
        try {
            backgroundMusic.pause()
        }
        catch (e: Exception){
            backgroundMusic.stop()
            backgroundMusic.reset()
            backgroundMusic.release()
        }
    }

    fun pauseFireworkSound(){
        if (fireworkSound.isPlaying)
            fireworkSound.pause()
    }

    fun stopBackgroundSound(){
        try {
            backgroundMusic.stop()
            backgroundMusic.reset()
            backgroundMusic.release()
        }catch (e: java.lang.Exception){
            
        }
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
        _musicState.postValue(state)
    }
}

sealed class MusicState(){
    class Play() : MusicState()
    class UpdateMusic(val localMusic: LocalMusicModel, val requestPlay: Boolean = true): MusicState()
    class Pause() : MusicState()
    class Stop(): MusicState()
}