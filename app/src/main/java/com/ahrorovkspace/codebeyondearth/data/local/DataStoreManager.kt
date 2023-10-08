package com.ahrorovkspace.codebeyondearth.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
    }
    suspend fun updateRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token
        }
    }
    suspend fun updateAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }
    val getRefreshToken = context.dataStore.data.map {
        it[REFRESH_TOKEN] ?: ""
    }
    val getAccessToken = context.dataStore.data.map {
        it[ACCESS_TOKEN] ?: ""
    }
}