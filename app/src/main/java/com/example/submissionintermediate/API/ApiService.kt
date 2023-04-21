package com.example.submissionintermediate.API

import androidx.lifecycle.LiveData
import com.example.submissionintermediate.DataClass.StoryModel
import com.example.submissionintermediate.DataClass.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun userLogin(
        @Body user: Map<String, String>
    ): Call<UserResponse>

    @POST("register")
    suspend fun userRegister(
        @Body user: Map<String, String>
    ): Call<UserResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 0,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Call<MutableList<StoryModel>>
}