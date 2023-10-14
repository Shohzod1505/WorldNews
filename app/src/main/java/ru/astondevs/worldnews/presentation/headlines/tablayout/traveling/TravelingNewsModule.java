package ru.astondevs.worldnews.presentation.headlines.tablayout.traveling;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.astondevs.worldnews.di.FeatureScope;
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey;
import ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.viewmodel.TravelingNewsViewModel;

@Module
public interface TravelingNewsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TravelingNewsViewModel.class)
    @FeatureScope
    ViewModel provideViewModel(TravelingNewsViewModel viewModel);

}
