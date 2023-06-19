package com.example.mynews.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mynews.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}