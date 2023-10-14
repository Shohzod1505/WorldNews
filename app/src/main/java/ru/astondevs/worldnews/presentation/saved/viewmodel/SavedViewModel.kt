package ru.astondevs.worldnews.presentation.saved.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.domain.remote.usecase.GetArticleUseCase
import ru.astondevs.worldnews.domain.remote.usecase.GetFilterNewsUseCase
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsUseCase
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem
import ru.astondevs.worldnews.presentation.common.adapter.toListAdapterArticle
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getArticleUseCase: GetArticleUseCase,
    private val getFilterNewsUseCase: GetFilterNewsUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Int>(View.GONE)
    val loading: LiveData<Int>
        get() = _loading

    private val _articleResponse = MutableLiveData<List<NewsItem.Article>>()
    val articleResponse: LiveData<List<NewsItem.Article>>
        get() = _articleResponse

    fun loadNews(query: String?, pageSize: Int?) {
        viewModelScope.launch {
            try {
                _loading.value = View.VISIBLE
                getArticleUseCase(query, pageSize).also {
                    _articleResponse.value = it.articles?.toListAdapterArticle()
                }
            } catch (error: Throwable) {
                //
            } finally {
                _loading.value = View.GONE
            }
        }
    }

    fun loadFilterNews(
        query: String?,
        sources: String?,
        pageSize: Int?,
        filter: String?,
        language: String?,
        startDate: String?,
        endDate: String?,
        )
    {
        viewModelScope.launch {
            try {
                _loading.value = View.VISIBLE
                getFilterNewsUseCase(query, sources, pageSize, filter, language, startDate, endDate).also {
                    _articleResponse.value = it.articles?.toListAdapterArticle()
                }
            } catch (error: Throwable) {
                //
            } finally {
                _loading.value = View.GONE
            }
        }
    }

    suspend fun getNews(query: String?) = getNewsUseCase(query)

    suspend fun getArticle(query: String?, pageSize: Int?) = getArticleUseCase(query, pageSize)

    suspend fun getFilter(
        query: String?,
        sources: String?,
        pageSize: Int?,
        filter: String?,
        language: String?,
        startDate: String?,
        endDate: String?,
    ) = getFilterNewsUseCase(query, sources, pageSize, filter, language, startDate, endDate)

    companion object {

        fun factory(
            getNewsUseCase: GetNewsUseCase,
            getArticleUseCase: GetArticleUseCase,
            getFilterNewsUseCase: GetFilterNewsUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return SavedViewModel(
                    getNewsUseCase,
                    getArticleUseCase,
                    getFilterNewsUseCase
                ) as T
            }
        }
    }

}
