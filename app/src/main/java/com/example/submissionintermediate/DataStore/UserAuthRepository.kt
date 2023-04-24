package com.example.submissionintermediate.DataStore

import com.example.submissionintermediate.API.ApiService
import com.example.submissionintermediate.DataStore.InterfaceRepository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserAuthRepository(
    private val api: ApiService
) : AuthRepository {

    override fun userRegister(name: String, email: String, password: String) = flow {
        val user: HashMap<String, String> = hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )
        val response = api.userRegister(user)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun userLogin(email: String, password: String) = flow {
        val user: HashMap<String, String> = hashMapOf(
            "email" to email,
            "password" to password
        )
        val response = api.userLogin(user)
        emit(response)
    }.flowOn(Dispatchers.IO)

}