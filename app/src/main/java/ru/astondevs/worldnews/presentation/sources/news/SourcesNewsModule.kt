package ru.astondevs.worldnews.presentation.sources.news

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.astondevs.worldnews.di.FeatureScope
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey
import ru.astondevs.worldnews.presentation.sources.news.viewmodel.SourcesNewsViewModel

@Module
interface SourcesNewsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SourcesNewsViewModel::class)
    @FeatureScope
    fun provideViewModel(viewModel: SourcesNewsViewModel): ViewModel
}
