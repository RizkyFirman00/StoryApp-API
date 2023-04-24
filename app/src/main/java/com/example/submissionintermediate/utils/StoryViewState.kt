package com.example.submissionintermediate.utils

import com.example.submissionintermediate.DataClass.Model.StoryModelEntity
import com.example.submissionintermediate.DataClass.StoryModel

data class StoryViewState(
    val resultStories: ResultMain<List<StoryModelEntity>> = ResultMain.Idle(),
    val name: String = "Not Set",
)
