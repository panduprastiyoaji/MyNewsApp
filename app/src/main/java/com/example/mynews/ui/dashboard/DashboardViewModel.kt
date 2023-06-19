package com.example.mynews.ui.dashboard

import androidx.lifecycle.*
import com.example.mynews.core.data.Resources
import com.example.mynews.core.domain.model.News
import com.example.mynews.core.domain.usecase.NewsUseCase

class DashboardViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    private var news: MutableLiveData<String> = MutableLiveData()
    fun search(q: String) {
        if (news.value == q) {
            return
        }
        news.value = q
    }

    val listNews: LiveData<Resources<List<News>>> = Transformations
        .switchMap(news) {
            newsUseCase.getAllNews(it).asLiveData()
        }
}