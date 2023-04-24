package com.example.submissionintermediate.DataStore.ViewModel

import androidx.lifecycle.*
import com.example.submissionintermediate.API.ApiClient
import com.example.submissionintermediate.DataClass.StoryModel
import com.example.submissionintermediate.DataStore.Controller.*
import com.example.submissionintermediate.DataStore.UserAuthRepository
import com.example.submissionintermediate.utils.StoryViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserController: GetUserController,
    private val storyListController: StoryListController,
    private val logOutController: LogOutController
) : ViewModel() {
    private val _storyListState = MutableStateFlow(StoryViewState())
    val storyListState = _storyListState.asStateFlow()

    fun getListStory() {
        viewModelScope.launch {
            storyListController().collect() { stories ->
                _storyListState.update {
                    it.copy(resultStories = stories)
                }
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            getUserController().collect { user ->
                _storyListState.update {
                    it.copy(name = user.name)
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutController()
        }
    }

    class Factory(
        private val getUserController: GetUserController,
        private val storyListController: StoryListController,
        private val logOutController: LogOutController
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                    getUserController,
                    storyListController,
                    logOutController
                ) as T
                else -> error("Unknown ViewModel class: $modelClass")
            }
        }
    }
}