package com.example.mynews.ui.detail

import androidx.lifecycle.ViewModel
import com.example.mynews.core.domain.model.News
import com.example.mynews.core.domain.usecase.NewsUseCase

class DetailNewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    fun setFavoriteNews(news: News, newStatus: Boolean) =
        newsUseCase.setFavoriteNews(news, newStatus)
}