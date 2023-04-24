package com.example.submissionintermediate.DataStore.Controller

import com.example.submissionintermediate.DataClass.Model.UserModelEntity
import com.example.submissionintermediate.DataStore.InterfaceRepository.UserRepositoryPreferences
import kotlinx.coroutines.flow.Flow

class GetUserController(private val userRepositoryPreferences: UserRepositoryPreferences) {
    operator fun invoke(): Flow<UserModelEntity> = userRepositoryPreferences.userData
}