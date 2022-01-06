package com.thanh_nguyen.test_count_down.app.data.data_source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.GsonBuilder
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.app.model.LocalMusicModel

object AppPreferences {
    val preferences: SharedPreferences = App.getInstance().getSharedPreferences("SapTetPreferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    private val IS_MUTED = "IS_MUTED"
    private val BACKGROUND_MUSIC = "BACKGROUND_MUSIC"

    var isBackgroundMuted: Boolean
        set(isBackgroundMuted){
            editor.putBoolean(IS_MUTED, isBackgroundMuted).commit()
        }
        get() = preferences.getBoolean(IS_MUTED, false)

    fun saveCurrentBackgroundMusic(music: LocalMusicModel){
        putObject(music, BACKGROUND_MUSIC)
    }

    fun getCurrentBackgroundMusic(): LocalMusicModel? = getObject(BACKGROUND_MUSIC)

    private fun putObject(`object`: Any?, key: String) {
        //Convert object to JSON String.
        try {
            val jsonString = GsonBuilder().create().toJson(`object`)
            //Save that String in SharedPreferences
            preferences.edit().putString(key, jsonString).apply()
        }catch (e:Throwable){}
    }

    fun clearPreference(key:String){
        preferences.edit().remove(key).apply()
    }

    inline fun <reified T> getObject(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        try {
            return GsonBuilder().create().fromJson(value, T::class.java)
        }catch (e:Throwable){
            return null
        }
    }

}