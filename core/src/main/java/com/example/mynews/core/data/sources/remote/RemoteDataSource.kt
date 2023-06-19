package com.example.mynews.core.data.sources.remote

import android.util.Log
import com.example.mynews.core.data.sources.remote.network.ApiResponse
import com.example.mynews.core.data.sources.remote.network.ApiService
import com.example.mynews.core.data.sources.remote.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getAllNews(q: String?): Flow<ApiResponse<List<ArticlesItem?>>> {
        return flow {
            try {
                val response = apiService.getList(q)
                val dataArray = response.articles
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}