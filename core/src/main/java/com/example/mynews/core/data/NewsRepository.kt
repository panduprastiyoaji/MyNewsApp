package com.example.mynews.core.data

import com.example.mynews.core.data.sources.local.LocalDataSource
import com.example.mynews.core.data.sources.remote.RemoteDataSource
import com.example.mynews.core.data.sources.remote.network.ApiResponse
import com.example.mynews.core.data.sources.remote.response.ArticlesItem
import com.example.mynews.core.domain.model.News
import com.example.mynews.core.domain.repository.INewsRepository
import com.example.mynews.core.utils.AppExecutors
import com.example.mynews.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {
    override fun getAllNews(q: String?): Flow<Resources<List<News>>> =
        object : NetworkBoundResources<List<News>, List<ArticlesItem?>>() {
            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getAllNews().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }
            override fun shouldFetch(data: List<News>?): Boolean =
                true
            override suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem?>>> =
                remoteDataSource.getAllNews(q)
            override suspend fun saveCallResult(data: List<ArticlesItem?>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asFlow()
    override fun getFavoriteNews(): Flow<List<News>> {
        return localDataSource.getFavoriteNews().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
    override fun setFavoriteNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute { localDataSource.setFavoriteNews(newsEntity, state) }
    }
}