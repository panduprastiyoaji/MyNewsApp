package com.example.mynews.core.utils

import com.example.mynews.core.data.sources.local.entity.NewsEntity
import com.example.mynews.core.data.sources.remote.response.ArticlesItem
import com.example.mynews.core.domain.model.News

object DataMapper {
    fun mapResponsesToEntities(input: List<ArticlesItem?>): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val news = NewsEntity(
                publishedAt = it?.publishedAt ?: "",
                author = it?.author ?: "",
                urlToImage = it?.urlToImage ?: "",
                description = it?.description ?: "",
                title = it?.title ?: "",
                url = it?.url ?: "",
                content = it?.content ?: "",
                isFavorite = false
            )
            newsList.add(news)
        }
        return newsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                publishedAt = it.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                description = it.description,
                title = it.title,
                url = it.url,
                content = it.content,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: News) = NewsEntity(
        publishedAt = input.publishedAt,
        author = input.author,
        urlToImage = input.urlToImage,
        description = input.description,
        title = input.title,
        url = input.url,
        content = input.content,
        isFavorite = input.isFavorite
    )
}