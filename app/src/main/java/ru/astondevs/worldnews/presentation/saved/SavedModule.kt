package ru.astondevs.worldnews.presentation.saved

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.astondevs.worldnews.di.FeatureScope
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey
import ru.astondevs.worldnews.presentation.saved.viewmodel.SavedViewModel

@Module
interface SavedModule {

    @Binds
    @IntoMap
    @ViewModelKey(SavedViewModel::class)
    @FeatureScope
    fun provideViewModel(viewModel: SavedViewModel): ViewModel
}
