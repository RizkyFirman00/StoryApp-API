package com.example.submissionintermediate.API

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        abstract val dataStore: DataStore<Preferences>
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(
                OkHttpClient.Builder()
                    .apply {
                        val loggingInterceptor = if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        } else {
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                        }

                        addInterceptor(AuthInterceptor(dataStore),
                        addInterceptor(loggingInterceptor)
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
    }
}