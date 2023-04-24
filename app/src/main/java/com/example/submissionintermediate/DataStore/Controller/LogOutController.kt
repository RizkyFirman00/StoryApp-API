package com.example.submissionintermediate.DataStore.Controller

import com.example.submissionintermediate.DataStore.InterfaceRepository.UserRepositoryPreferences

class LogOutController(private val userRepositoryPreferences: UserRepositoryPreferences) {
    suspend operator fun invoke() = userRepositoryPreferences.deleteUserData()
}