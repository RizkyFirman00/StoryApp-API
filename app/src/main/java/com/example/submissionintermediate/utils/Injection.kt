package com.example.submissionintermediate.utils

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionintermediate.API.ApiClient
import com.example.submissionintermediate.DataStore.Controller.*
import com.example.submissionintermediate.DataStore.UserAuthRepository
import com.example.submissionintermediate.DataStore.UserRepository
import com.example.submissionintermediate.DataStore.UserStoryRepository
import com.example.submissionintermediate.DataStore.ViewModel.AuthViewModel
import com.example.submissionintermediate.DataStore.ViewModel.MainViewModel

object Injection {
    private var application: Application? = null

    private inline val requireApplication
        get() = application ?: error("Missing call: initWith(application)")

    fun initWith(application: Application) {
        this.application = application
    }

    // Data Store
    private val Context.dataStore by preferencesDataStore(name = "data_store")

    // ViewModel Factory
    val autViewModelFactory
        get() = AuthViewModel.Factory(
            loginController = loginController,
            registerController = registerController
        )
    val storyViewModelFactory
        get() = MainViewModel.Factory(
            storyListController = storyListController,
            getUserController = getUserController,
            logOutController = logOutController
        )
//    val storyDetailViewModelFactory
//        get() = StoryDetailViewModel.Factory(
//            getStoryDetailUseCase = getStoryDetailUseCase
//        )
//    val addStoryViewModelFactory
//        get() = AddStoryViewModel.Factory(
//            addStoryUseCase = addStoryUseCase
//        )

    // Controller Injection
    private val loginController get() = LoginController(authRepository, userPreferencesRepository)
    private val registerController get() = RegisterController(authRepository)
    private val storyListController get() = StoryListController(storyRepository)
    private val getUserController get() = GetUserController(userPreferencesRepository)
    private val logOutController get() = LogOutController(userPreferencesRepository)
//    private val getStoryDetailUseCase get() = GetStoryDetailUseCase(storyRepository)
//    private val addStoryUseCase get() = AddStoryUseCase(storyRepository)

    // Repository Injection
    private val userPreferencesRepository by lazy {
        UserRepository(requireApplication.dataStore)
    }
    private val authRepository by lazy {
        UserAuthRepository(ApiClient(requireApplication.dataStore).apiService)
    }
    private val storyRepository by lazy {
        UserStoryRepository(ApiClient(requireApplication.dataStore).apiService)
    }
}