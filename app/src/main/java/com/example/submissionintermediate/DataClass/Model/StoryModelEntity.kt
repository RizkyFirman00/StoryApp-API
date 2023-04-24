package com.example.submissionintermediate.DataClass.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoryModelEntity(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
) : Parcelable
