package com.example.submissionintermediate.DataStore.InterfaceRepository

import com.example.submissionintermediate.DataClass.LoginResponse
import com.example.submissionintermediate.DataClass.ResponseMain
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun userRegister(email: String, password: String, name: String): Flow<ResponseMain>
    fun userLogin(email: String, password: String): Flow<LoginResponse>
}