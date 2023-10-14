package ru.astondevs.worldnews.presentation.common.adapter

import ru.astondevs.worldnews.data.datasource.remote.response.Source as SourceResponse

sealed class NewsItem {

    abstract val viewType: Int
    abstract val itemId: Long

    data class Article(
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val source: SourceResponse?,
        val title: String?,
        val url: String?,
        val urlToImage: String?
    ) : NewsItem() {
        override val viewType = VIEW_TYPE_ARTICLE
        override val itemId = title.hashCode().toLong()
    }

    data class Source(
        val category: String?,
        val country: String?,
        val description: String?,
        val id: String?,
        val language: String?,
        val name: String?,
        val url: String?
    ) : NewsItem() {
        override val viewType = VIEW_TYPE_SOURCE
        override val itemId = name.hashCode().toLong()
    }

    companion object {
        const val VIEW_TYPE_ARTICLE = 1
        const val VIEW_TYPE_SOURCE = 2
    }

}
