package ru.astondevs.worldnews.presentation.common.adapter

import ru.astondevs.worldnews.data.datasource.remote.response.Article
import ru.astondevs.worldnews.data.datasource.remote.response.Source

fun Source.toAdapterSource(): NewsItem.Source = NewsItem.Source(
    category = category,
    country = country,
    description = description,
    id = id,
    language = language,
    name = name,
    url = url
)


fun List<Source>.toListAdapterSource(): List<NewsItem.Source> = map {
    it.toAdapterSource()
}

fun Article.toAdapterArticle(): NewsItem.Article = NewsItem.Article(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = source,
    title = title,
    url = url,
    urlToImage = urlToImage
)

fun List<Article>.toListAdapterArticle(): List<NewsItem.Article> = map {
    it.toAdapterArticle()
}
