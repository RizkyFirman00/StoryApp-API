package com.example.submissionintermediate.DataStore.Controller

import com.example.submissionintermediate.DataClass.Model.StoryModelEntity
import com.example.submissionintermediate.DataStore.InterfaceRepository.StoryRepository
import com.example.submissionintermediate.utils.ResultMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class StoryListController(private val storyRepo: StoryRepository) {
    operator fun invoke(): Flow<ResultMain<List<StoryModelEntity>>> = flow {
        emit(ResultMain.Loading())
        storyRepo.getListStory().map {
            it.listStory.map { story ->
                StoryModelEntity(
                    id = story.id,
                    name = story.name,
                    description = story.description,
                    photoUrl = story.photoUrl,
                )
            }
        }.catch {
            emit(ResultMain.Error(it.message.toString()))
        }.collect {
            emit(ResultMain.Success(it))
        }
    }
}
