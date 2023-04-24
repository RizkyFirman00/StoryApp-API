package com.example.submissionintermediate.DataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.submissionintermediate.DataClass.Model.UserModelEntity
import com.example.submissionintermediate.DataStore.InterfaceRepository.UserRepositoryPreferences
import kotlinx.coroutines.flow.*

class UserRepository(
    private val dataStore: DataStore<Preferences>
) : UserRepositoryPreferences {

    private object Key {
        val USERID = stringPreferencesKey("user_id")
        val TOKEN_USER = stringPreferencesKey("token")
        val NAME_USER = stringPreferencesKey("name")
        val EMAIL_USER = stringPreferencesKey("email")
        val DEFAULT_VALUE = stringPreferencesKey("not_set_yet")
    }

    private inline val Preferences.id
        get() = this[Key.USERID] ?: this[Key.DEFAULT_VALUE]
    private inline val Preferences.name
        get() = this[Key.NAME_USER] ?: this[Key.DEFAULT_VALUE]
    private inline val Preferences.email
        get() = this[Key.EMAIL_USER] ?: this[Key.DEFAULT_VALUE]
    private inline val Preferences.token
        get() = this[Key.TOKEN_USER] ?: this[Key.DEFAULT_VALUE]

    override val userData: Flow<UserModelEntity> = dataStore.data.map {
        UserModelEntity(
            userId = it.id ?: "not_set_yet",
            name = it.name ?: "not_set_yet",
            token = it.token ?: "not_set_yet",
        )
    }.distinctUntilChanged()

    override suspend fun saveUserData(user: UserModelEntity) {
        dataStore.edit {
            it[Key.USERID] = user.userId
            it[Key.NAME_USER] = user.name
            it[Key.TOKEN_USER] = user.token
        }
    }

    override suspend fun deleteUserData() {
        dataStore.edit {
            it.clear()
        }
    }
}