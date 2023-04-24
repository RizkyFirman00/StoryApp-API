package com.example.submissionintermediate.API

import com.example.submissionintermediate.DataClass.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun userLogin(
        @Body user: HashMap<String, String>
    ): LoginResponse

    @POST("register")
    suspend fun userRegister(
        @Body user: HashMap<String, String>
    ): ResponseMain

    @GET("stories")
    suspend fun getListStory(): StoryResponse

    @GET("stories/{id}")
    suspend fun getStoryDetail(
        @Path("id") id: String
    ): StoryDetailResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): ResponseMain
}