package ru.astondevs.worldnews.presentation.common.filter

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.astondevs.worldnews.di.FeatureScope
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey
import ru.astondevs.worldnews.presentation.common.filter.viewmodel.FilterViewModel

@Module
interface FilterModule {

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    @FeatureScope
    fun provideViewModel(viewModel: FilterViewModel): ViewModel

}
