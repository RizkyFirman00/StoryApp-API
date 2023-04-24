package com.example.submissionintermediate.DataClass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryModel>
)

data class StoryDetailResponse(
    val error: Boolean,
    val message: String,
    val story: StoryModel
)


@Entity(tableName = "user_story")
data class StoryModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val description :String,
    val photoUrl: String,
    val created_at: String,
    val latitude: String,
    val longitude: String,
)
