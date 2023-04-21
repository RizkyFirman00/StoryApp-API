package com.example.submissionintermediate.DataClass

data class UserResponse(
    val error: String,
    val message: String,
    val loginResult: UserModel,
    val listStory: ArrayList<StoryModel>
)
