package com.dennydev.spotyrex.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "auth")
@ViewModelScoped
class AuthStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val flowToken: Flow<String> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[tokenKey] ?: ""
        }

    val flowExpired: Flow<Int> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[expiredKey] ?: 0
        }

    suspend fun saveToken(token: String, exp: Int) {
        dataStore.edit { settings ->
            settings[tokenKey] = token
            settings[expiredKey] = exp
        }
    }

    suspend fun removeToken(){
        dataStore.edit {
            it[tokenKey] = ""
            it[expiredKey] = 0
        }
    }

    companion object PreferencesKey{
        val tokenKey = stringPreferencesKey("_token")
        val expiredKey = intPreferencesKey("_exp")
    }
}