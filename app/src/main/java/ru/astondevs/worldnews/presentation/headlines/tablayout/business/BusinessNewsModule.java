package ru.astondevs.worldnews.presentation.headlines.tablayout.business;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.astondevs.worldnews.di.FeatureScope;
import ru.astondevs.worldnews.di.viewmodel.ViewModelKey;
import ru.astondevs.worldnews.presentation.headlines.tablayout.business.viewmodel.BusinessNewsViewModel;

@Module
public interface BusinessNewsModule {

    @Binds
    @IntoMap
    @ViewModelKey(BusinessNewsViewModel.class)
    @FeatureScope
    ViewModel provideViewModel(BusinessNewsViewModel viewModel);

}
