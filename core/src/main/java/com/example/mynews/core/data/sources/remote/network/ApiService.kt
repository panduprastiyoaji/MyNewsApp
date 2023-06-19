package com.example.mynews.core.data.sources.remote.network

import com.example.mynews.core.data.sources.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getList(
        @Query("q") q: String?
    ): NewsResponse
}