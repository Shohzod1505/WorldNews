package ru.astondevs.worldnews.presentation.headlines.tablayout.general;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.astondevs.worldnews.di.FeatureScope;
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey;
import ru.astondevs.worldnews.presentation.headlines.tablayout.general.viewmodel.GeneralNewsViewModel;

@Module
public interface GeneralNewsModule {

    @Binds
    @IntoMap
    @ViewModelKey(GeneralNewsViewModel.class)
    @FeatureScope
    ViewModel provideViewModel(GeneralNewsViewModel viewModel);

}
