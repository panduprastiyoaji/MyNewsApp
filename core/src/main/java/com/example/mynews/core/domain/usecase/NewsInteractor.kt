package com.example.mynews.core.domain.usecase

import com.example.mynews.core.domain.model.News
import com.example.mynews.core.domain.repository.INewsRepository

class NewsInteractor(private val newsRepository: INewsRepository) : NewsUseCase {
    override fun getAllNews(q: String?) = newsRepository.getAllNews(q)
    override fun getFavoriteNews() = newsRepository.getFavoriteNews()
    override fun setFavoriteNews(news: News, state: Boolean) =
        newsRepository.setFavoriteNews(news, state)
}