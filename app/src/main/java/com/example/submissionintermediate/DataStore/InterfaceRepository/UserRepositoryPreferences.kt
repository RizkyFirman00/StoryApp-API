package com.example.submissionintermediate.DataStore.InterfaceRepository

import com.example.submissionintermediate.DataClass.Model.UserModelEntity
import kotlinx.coroutines.flow.Flow

interface UserRepositoryPreferences {
    val userData: Flow<UserModelEntity>
    suspend fun saveUserData(UserModelEntity: UserModelEntity)
    suspend fun deleteUserData()
}