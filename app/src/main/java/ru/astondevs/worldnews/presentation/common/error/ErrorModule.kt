package ru.astondevs.worldnews.presentation.common.error

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.astondevs.worldnews.di.FeatureScope
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey
import ru.astondevs.worldnews.presentation.common.error.viewmodel.ErrorViewModel

@Module
interface ErrorModule {

    @Binds
    @IntoMap
    @ViewModelKey(ErrorViewModel::class)
    @FeatureScope
    fun provideViewModel(viewModel: ErrorViewModel): ViewModel

}
