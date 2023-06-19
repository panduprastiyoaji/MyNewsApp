package com.example.mynews.core.data.sources.local

import com.example.mynews.core.data.sources.local.entity.NewsEntity
import com.example.mynews.core.data.sources.local.room.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val newsDao: NewsDao) {
    fun getAllNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()
    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()
    suspend fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)
    fun setFavoriteNews(news: NewsEntity, newState: Boolean) {
        news.isFavorite = newState
        newsDao.updateFavoriteNews(news)
    }
}