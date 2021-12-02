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

}

object AppSharedPreferences {
    private fun <T> getByKey(key: Preferences.Key<T>, defaultValue: T? = null) = App.getInstance().dataStore.data.map { preferences ->
        preferences[key] ?: defaultValue
    }

    private suspend fun <T> setKey(key: Preferences.Key<T>, value: T){
        App.getInstance().dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    val isVisited = getByKey(PreferencesKey.IS_VISITED, false)
    suspend fun setIsVisited(isVisited: Boolean) = setKey(PreferencesKey.IS_VISITED, isVisited)

    val isClosedCountDownNoti = getByKey(PreferencesKey.IS_CLOSED_COUNT_DOWN_NOTI, false)
    suspend fun setisClosedCountDownNoti(isClosedCountDownNoti: Boolean) = setKey(PreferencesKey.IS_CLOSED_COUNT_DOWN_NOTI, isClosedCountDownNoti)
}