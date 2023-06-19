package com.example.mynews.core.domain.usecase

import com.example.mynews.core.data.Resources
import com.example.mynews.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(q: String?): Flow<Resources<List<News>>>
    fun getFavoriteNews(): Flow<List<News>>
    fun setFavoriteNews(news: News, state: Boolean)
}