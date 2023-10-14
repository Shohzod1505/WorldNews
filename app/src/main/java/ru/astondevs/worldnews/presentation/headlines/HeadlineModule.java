package ru.astondevs.worldnews.presentation.headlines;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.astondevs.worldnews.di.FeatureScope;
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey;
import ru.astondevs.worldnews.presentation.headlines.viewmodel.HeadlineViewModel;

@Module
public interface HeadlineModule {

    @Binds
    @IntoMap
    @ViewModelKey(HeadlineViewModel.class)
    @FeatureScope
    ViewModel provideViewModel(HeadlineViewModel viewModel);

}
