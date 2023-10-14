package ru.astondevs.worldnews.presentation.common.detail

import ru.astondevs.worldnews.presentation.common.adapter.NewsItem

interface DetailFragmentNavigationListener {

    fun goToDetailFragment(article: String?)

}
