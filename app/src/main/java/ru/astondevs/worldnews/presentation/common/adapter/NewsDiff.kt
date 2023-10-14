package ru.astondevs.worldnews.presentation.common.adapter

import androidx.recyclerview.widget.DiffUtil

object NewsDiff : DiffUtil.ItemCallback<NewsItem>(){
    override fun areItemsTheSame(
        oldItem: NewsItem,
        newItem: NewsItem
    ): Boolean = oldItem.itemId == newItem.itemId
//    = oldItem.viewType == newItem.viewType && when(oldItem) {
//        is NewsItem.Article -> oldItem.title == (newItem as? NewsItem.Article)?.title
//        is NewsItem.Source -> oldItem.name == (newItem as? NewsItem.Source)?.name
//    }

    override fun areContentsTheSame(
        oldItem: NewsItem,
        newItem: NewsItem,
    ): Boolean = oldItem == newItem
}
