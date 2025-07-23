package com.example.project5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    fun getApod(
        @Query("api_key") apiKey: String,
        @Query("count") count: Int = 10
    ): Call<List<ApodResponse>>
}
