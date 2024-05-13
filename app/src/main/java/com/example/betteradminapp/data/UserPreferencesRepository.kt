package com.example.betteradminapp.data


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
        val USERID = intPreferencesKey("user_id")
    }

    suspend fun readEmail(): String? {
        val preferences = dataStore.data.first()
        return preferences[EMAIL]
    }

    suspend fun readPassword(): String? {
        val preferences = dataStore.data.first()
        return preferences[PASSWORD]
    }

    suspend fun readUserId(): Int? {
        val preferences = dataStore.data.first()
        return preferences[USERID]
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    suspend fun savePassword(password: String) {
        dataStore.edit { preferences ->
            preferences[PASSWORD] = password
        }
    }

    suspend fun saveUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[USERID] = userId
        }
    }
}