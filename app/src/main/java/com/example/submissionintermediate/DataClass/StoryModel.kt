package com.example.submissionintermediate.DataClass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_story")
data class StoryModel(
    @PrimaryKey
    val storyId: String,
    val name: String,
    val description :String,
    val photo_url: String,
    val created_at: String,
    val latitude: String,
    val longitude: String,
) : Parcelable
