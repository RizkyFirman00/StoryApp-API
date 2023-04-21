package com.example.submissionintermediate.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.API.ApiClient.apiService
import com.example.submissionintermediate.DataClass.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserRepository(
    context: Context
) {
    private val dataStore = context.dataStore

    /**
     * Repository for User Data
     */

    fun getUserToken(): LiveData<String> = getToken().asLiveData()
    suspend fun saveUserToken(value: String) = saveToken(value)
    fun getUserName(): LiveData<String> = getName().asLiveData()
    suspend fun saveUserName(value: String) = saveName(value)
    fun getUserEmail(): LiveData<String> = getEmail().asLiveData()
    suspend fun saveUserEmail(value: String) = saveEmail(value)
    suspend fun clearUserData() = clearData()

    /**
     * Repository for API
     */

    fun userRegister(name: String, email: String, password: String): Call<UserResponse> {
        val user: Map<String, String> = mapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )
        return apiService.userRegister(user)
    }

    fun userLogin(email: String, password: String): Call<UserResponse> {
        val user: Map<String, String> = mapOf(
            "email" to email,
            "password" to password
        )
        return apiService.userLogin(user)
    }

    /**
     * DataStore
     */

    companion object {
        private val USERID = stringPreferencesKey("user_id")
        private val TOKEN_USER = stringPreferencesKey("token")
        private val NAME_USER = stringPreferencesKey("name")
        private val EMAIL_USER = stringPreferencesKey("email")
        private val PASSWORD_USER = stringPreferencesKey("password")
        const val DEFAULT_VALUE = "not_set_yet"

    }

    private fun getToken(): Flow<String> = dataStore.data.map {
        it[TOKEN_USER] ?: DEFAULT_VALUE
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_USER] = token
        }
    }

    private fun getName(): Flow<String> = dataStore.data.map {
        it[NAME_USER] ?: DEFAULT_VALUE
    }

    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[NAME_USER] = name
        }
    }

    private fun getEmail(): Flow<String> = dataStore.data.map {
        it[EMAIL_USER] ?: DEFAULT_VALUE
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_USER] = email
        }
    }

    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}