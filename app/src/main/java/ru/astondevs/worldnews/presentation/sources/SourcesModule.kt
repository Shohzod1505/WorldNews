package ru.astondevs.worldnews.presentation.sources

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.astondevs.worldnews.di.FeatureScope
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey
import ru.astondevs.worldnews.presentation.sources.viewmodel.SourcesViewModel

@Module
interface SourcesModule {

    @Binds
    @IntoMap
    @ViewModelKey(SourcesViewModel::class)
    @FeatureScope
    fun provideViewModel(viewModel: SourcesViewModel): ViewModel
}
