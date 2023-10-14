package ru.astondevs.worldnews.presentation.common.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsUseCase
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
//    private val saveArticleUseCase: SaveArticleUseCase,
//    private val deleteArticleUseCase: DeleteArticleUseCase,
) : ViewModel() {

    suspend fun getNews(query: String?) = getNewsUseCase(query)

//    suspend fun saveArticle(article: Article) = saveArticleUseCase(article)
//
//    suspend fun deleteArticle(article: Article) = deleteArticleUseCase(article)

    companion object {

        fun factory(
            getNewsUseCase: GetNewsUseCase
//            saveArticleUseCase: SaveArticleUseCase,
//            deleteArticleUseCase: DeleteArticleUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return DetailViewModel(
                    getNewsUseCase,
//                    saveArticleUseCase,
//                    deleteArticleUseCase,
                ) as T
            }
        }
    }

}
