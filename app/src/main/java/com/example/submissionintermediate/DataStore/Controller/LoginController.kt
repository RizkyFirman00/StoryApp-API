package com.example.submissionintermediate.DataStore.Controller

import android.util.Log
import com.example.submissionintermediate.DataClass.Model.UserModelEntity
import com.example.submissionintermediate.DataStore.InterfaceRepository.AuthRepository
import com.example.submissionintermediate.DataStore.InterfaceRepository.UserRepositoryPreferences
import com.example.submissionintermediate.utils.ResultMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginController(
    private val authRepo: AuthRepository,
    private val userRepoPref: UserRepositoryPreferences
) {
    operator fun invoke(email: String, password: String): Flow<ResultMain<String>> = flow {
        emit(ResultMain.Loading())
        authRepo.userLogin(email, password).catch {
            emit(ResultMain.Error(it.message.toString()))
        }.collect { result ->
            if (result.error) {
                emit(ResultMain.Error(result.message))
            } else {
                result.loginResult.let {
                    userRepoPref.saveUserData(
                        UserModelEntity(
                            it.userId, it.name, it.token,
                        )
                    )
                }
                Log.d("LoginController", "invoke: name:${result.loginResult?.name} userId:${result.loginResult?.userId} token:${result.loginResult?.token}")
                emit(ResultMain.Success(result.message))
            }
        }
    }
}