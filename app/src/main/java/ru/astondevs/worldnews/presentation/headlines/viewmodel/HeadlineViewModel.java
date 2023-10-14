package ru.astondevs.worldnews.presentation.headlines.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import javax.inject.Inject;

import ru.astondevs.worldnews.domain.remote.usecase.GetArticleUseCase;
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsUseCase;

public class HeadlineViewModel extends ViewModel {

    private final GetNewsUseCase getNewsUseCase;
    private final GetArticleUseCase getArticleUseCase;

    @Inject
    public HeadlineViewModel(
            GetNewsUseCase getNewsUseCase,
            GetArticleUseCase getArticleUseCase) {
        this.getNewsUseCase = getNewsUseCase;
        this.getArticleUseCase = getArticleUseCase;
    }

    public static ViewModelProvider.Factory factory(GetNewsUseCase getNewsUseCase, GetArticleUseCase getArticleUseCase) {
        return new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
                return (T) new HeadlineViewModel(getNewsUseCase, getArticleUseCase);
            }
        };
    }

}
