package com.thanh_nguyen.test_count_down.app.data.data_source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.thanh_nguyen.test_count_down.App
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppSharedPreferences")

object PreferencesKey{
    val IS_VISITED = booleanPreferencesKey("isVisited")
    val IS_CLOSED_COUNT_DOWN_NOTI = booleanPreferencesKey("IS_CLOSED_COUNT_DOWN_NOTI")
    val IS_SHOWED_INSTRUCTION = booleanPreferencesKey("IS_SHOWED_INSTRUCTION")
    val IS_MUTED = booleanPreferencesKey("IS_MUTED")
}

object AppSharedPreferences {
    private fun <T> getByKey(key: Preferences.Key<T>, defaultValue: T? = null) = App.getInstance().dataStore.data.map { preferences ->
        preferences[key] ?: defaultValue
    }

    private suspend fun <T> setKey(key: Preferences.Key<T>, value: T): T?{
       return App.getInstance().dataStore.edit { prefs ->
            prefs[key] = value
        }[key]
    }

    val isVisited = getByKey(PreferencesKey.IS_VISITED, false)
    suspend fun setIsVisited(isVisited: Boolean) = setKey(PreferencesKey.IS_VISITED, isVisited)

    val isClosedCountDownNoti = getByKey(PreferencesKey.IS_CLOSED_COUNT_DOWN_NOTI, false)
    suspend fun setisClosedCountDownNoti(isClosedCountDownNoti: Boolean) = setKey(PreferencesKey.IS_CLOSED_COUNT_DOWN_NOTI, isClosedCountDownNoti)

    val isShowedInstruction = getByKey(PreferencesKey.IS_SHOWED_INSTRUCTION, false)
    suspend fun setIsShowedInstruction(isShowedInstruction: Boolean) = setKey(PreferencesKey.IS_SHOWED_INSTRUCTION, isShowedInstruction)

    val isMuted = getByKey(PreferencesKey.IS_MUTED, false)
    suspend fun setIsMuted(isMuted: Boolean) = setKey(PreferencesKey.IS_MUTED, isMuted)


}