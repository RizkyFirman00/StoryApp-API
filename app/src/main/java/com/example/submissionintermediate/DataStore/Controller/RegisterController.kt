package com.example.submissionintermediate.DataStore.Controller

import android.util.Log
import com.example.submissionintermediate.DataStore.InterfaceRepository.AuthRepository
import com.example.submissionintermediate.utils.ResultMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RegisterController(
    private val authRepo : AuthRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Flow<ResultMain<String>> =
        flow {
            emit(ResultMain.Loading())
            authRepo.userRegister(
                name, email, password
            ).catch {
                emit(ResultMain.Error(it.message.toString()))
            }.collect { result ->
                if (result.error) {
                    emit(ResultMain.Error(result.message))
                } else {
                    emit(ResultMain.Success(result.message))
                }
            }
        }
}