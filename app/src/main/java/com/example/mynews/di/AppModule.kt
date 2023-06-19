package com.example.mynews.di

import com.example.mynews.core.domain.usecase.NewsInteractor
import com.example.mynews.core.domain.usecase.NewsUseCase
import com.example.mynews.ui.detail.DetailNewsViewModel
import com.example.mynews.ui.dashboard.DashboardViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { DetailNewsViewModel(get()) }
}