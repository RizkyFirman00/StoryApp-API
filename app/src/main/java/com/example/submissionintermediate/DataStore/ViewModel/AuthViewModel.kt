package com.example.submissionintermediate.DataStore.ViewModel

import androidx.lifecycle.*
import com.example.submissionintermediate.DataStore.Controller.LoginController
import com.example.submissionintermediate.DataStore.Controller.RegisterController
import com.example.submissionintermediate.utils.UserViewState
import kotlinx.coroutines.flow.*

class AuthViewModel(
    private val loginController: LoginController,
    private val registerController: RegisterController
    ) : ViewModel() {

    private val _loginResponse = MutableStateFlow(UserViewState())
    val loginResponse = _loginResponse.asStateFlow()
    private val _registerResponse = MutableStateFlow(UserViewState())
    val registerResponse = _registerResponse.asStateFlow()

    fun login(email: String, password: String) {
        loginController(email, password)
            .onEach {result ->
                _loginResponse.update {
                    it.copy(resultVerifyUser = result)
                }
            }.launchIn(viewModelScope)
    }

    fun register(name: String, email: String, password: String) {
        registerController(name, email, password)
            .onEach { result ->
                _registerResponse.update {
                    it.copy(resultVerifyUser = result)
                }
            }.launchIn(viewModelScope)
    }

    class Factory(
        private val loginController: LoginController,
        private val registerController: RegisterController
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(loginController, registerController) as T
                else -> error("Unknown ViewModel class: $modelClass")
            }
        }
    }
}