package ru.astondevs.worldnews.presentation.common.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.astondevs.worldnews.di.FeatureScope
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey
import ru.astondevs.worldnews.presentation.common.detail.viewmodel.DetailViewModel

@Module
interface DetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    @FeatureScope
    fun provideViewModel(viewModel: DetailViewModel): ViewModel

}
