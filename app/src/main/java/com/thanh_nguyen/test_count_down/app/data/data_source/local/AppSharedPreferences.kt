package com.thanh_nguyen.test_count_down.app.data.data_source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thanh_nguyen.test_count_down.App
import com.thanh_nguyen.test_count_down.app.model.MusicModel
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppSharedPreferences")

object PreferencesKey{
    val IS_VISITED = booleanPreferencesKey("isVisited")
    val IS_CLOSED_COUNT_DOWN_NOTI = booleanPreferencesKey("IS_CLOSED_COUNT_DOWN_NOTI")
    val IS_SHOWED_INSTRUCTION = booleanPreferencesKey("IS_SHOWED_INSTRUCTION")
    val IS_MUTED = booleanPreferencesKey("IS_MUTED")
    val BACKGROUND_MUSIC = stringPreferencesKey("BACKGROUND_MUSIC")
}

object AppSharedPreferences {
    private fun <T> getByKey(key: Preferences.Key<T>, defaultValue: T? = null) = App.getInstance().dataStore.data.map { preferences ->
        preferences[key] ?: defaultValue
    }

    private inline fun <reified T> getObjectByKey(key: Preferences.Key<String>) = App.getInstance().dataStore.data.map { preferences ->
        var result: T? = null
        try{
            val dataStr = preferences[key]
            result = GsonBuilder().create().fromJson(dataStr, T::class.java)
        }
        catch (e: Exception){
        }
        result
    }

    private suspend fun <T> setKey(key: Preferences.Key<T>, value: T): T?{
       return App.getInstance().dataStore.edit { prefs ->
            prefs[key] = value
        }[key]
    }

    private suspend inline fun <reified T> setObjectKey(key: Preferences.Key<String>, value: T){
        App.getInstance().dataStore.edit { prefs ->
            prefs[key] = Gson().toJson(value)
        }
    }

    val isVisited = getByKey(PreferencesKey.IS_VISITED, false)
    suspend fun setIsVisited(isVisited: Boolean) = setKey(PreferencesKey.IS_VISITED, isVisited)

    val isClosedCountDownNoti = getByKey(PreferencesKey.IS_CLOSED_COUNT_DOWN_NOTI, false)
    suspend fun setisClosedCountDownNoti(isClosedCountDownNoti: Boolean) = setKey(PreferencesKey.IS_CLOSED_COUNT_DOWN_NOTI, isClosedCountDownNoti)

    val isShowedInstruction = getByKey(PreferencesKey.IS_SHOWED_INSTRUCTION, false)
    suspend fun setIsShowedInstruction(isShowedInstruction: Boolean) = setKey(PreferencesKey.IS_SHOWED_INSTRUCTION, isShowedInstruction)

    val isMuted = getByKey(PreferencesKey.IS_MUTED, false)
    suspend fun setIsMuted(isMuted: Boolean) = setKey(PreferencesKey.IS_MUTED, isMuted)


    val getBackgroundMusic = getObjectByKey<MusicModel>(PreferencesKey.BACKGROUND_MUSIC)
    suspend fun setBackgroundMusic(music: MusicModel) = setObjectKey(PreferencesKey.BACKGROUND_MUSIC, music)
}