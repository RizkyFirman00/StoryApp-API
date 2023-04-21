package com.example.submissionintermediate.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.submissionintermediate.DataClass.UserResponse
import com.example.submissionintermediate.DataStore.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private val userRepo: UserRepository) : ViewModel() {

    val user = MutableLiveData<UserResponse>()
    private var _error = MutableLiveData<Boolean>()
    val error: MutableLiveData<Boolean> = _error

    fun userAuthLogin(email: String, password: String) {
        val client = userRepo.userLogin(email, password)
        viewModelScope.launch(Dispatchers.Main) {
            client.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()?.loginResult
                        Log.d("AuthenticationViewModel", "onResponse Success : $responseBody")
                        user.postValue(response.body())
                    } else {
                        Log.d("AuthenticationViewModel", "onResponse Fail: " + response.message())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("AuthenticationViewModel", "onFailure: " + t.message)
                }
            })
        }
    }

    fun userRegister(
        name: String,
        email: String,
        password: String
    ) {
        val client = userRepo.userRegister(name, email, password)
        viewModelScope.launch(Dispatchers.Main) {
            client.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Log.e("AuthenticationViewModel", "onResponse: " + response.body())
                    if (response.isSuccessful) {
                        Log.d("AuthenticationViewModel", "onResponse Success : " + response.body())
                    } else {
                        Log.d("AuthenticationViewModel", "onResponse Fail: " + response.message())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("AuthenticationViewModel", "onFailure: " + t.message)
                }
            })
        }
    }

    fun getToken() = userRepo.getUserToken()

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.saveToken(token)
            Log.d("saveToken FUN", "onResponse Success : $token")
        }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.saveEmail(email)
        }
    }

    fun saveName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.saveName(name)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.clearUserData()
        }
    }

    class Factory(private val userRepo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(userRepo) as T
        }
    }
}