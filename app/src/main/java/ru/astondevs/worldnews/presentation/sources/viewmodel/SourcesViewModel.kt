package ru.astondevs.worldnews.presentation.sources.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.astondevs.worldnews.domain.remote.usecase.GetSourcesUseCase
import javax.inject.Inject

class SourcesViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase
) : ViewModel() {

    suspend fun getSources() = getSourcesUseCase()

    companion object {

        fun factory(
            getSourcesUseCase: GetSourcesUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return SourcesViewModel(
                    getSourcesUseCase,
                ) as T
            }
        }
    }

}
