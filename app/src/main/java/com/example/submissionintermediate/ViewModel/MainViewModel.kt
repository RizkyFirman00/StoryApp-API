package com.example.submissionintermediate.ViewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.*
import com.example.submissionintermediate.API.ApiClient
import com.example.submissionintermediate.DataClass.StoryModel
import com.example.submissionintermediate.DataStore.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class MainViewModel(
    private val userRepository: UserRepository,
    ) : ViewModel() {
    private val _storiesUser = MutableLiveData<MutableList<StoryModel>>()
    val storiesUser: MutableLiveData<MutableList<StoryModel>> = _storiesUser

    fun getUserToken() = userRepository.getUserToken()
    fun getUserName(): LiveData<String> = userRepository.getUserName()

    fun getStories(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val stories = ApiClient.
            _storiesUser.postValue(stories)
        }
    }

    class Factory(private val userRepo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(userRepo) as T
        }
    }

}