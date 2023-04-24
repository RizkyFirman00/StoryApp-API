package com.example.submissionintermediate.DataStore

import com.example.submissionintermediate.API.ApiService
import com.example.submissionintermediate.DataStore.InterfaceRepository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class UserStoryRepository(private val api: ApiService): StoryRepository {
    override fun getListStory() = flow{
        val response = api.getListStory()
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getStoryDetail(id: String) = flow {
        val response = api.getStoryDetail(id)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun addStory(file: String, description: String) = flow {
        val response = MultipartBody.Part.createFormData(
            "photo", file, file.toRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        emit(api.addStory(response, description.toRequestBody("text/plain".toMediaTypeOrNull())))
    }
}