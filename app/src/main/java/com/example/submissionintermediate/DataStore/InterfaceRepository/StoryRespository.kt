package com.example.submissionintermediate.DataStore.InterfaceRepository

import com.example.submissionintermediate.DataClass.ResponseMain
import com.example.submissionintermediate.DataClass.StoryDetailResponse
import com.example.submissionintermediate.DataClass.StoryModel
import com.example.submissionintermediate.DataClass.StoryResponse
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getListStory(): Flow<StoryResponse>
    fun getStoryDetail(id: String): Flow<StoryDetailResponse>
    fun addStory(file: String, description: String): Flow<ResponseMain>
}