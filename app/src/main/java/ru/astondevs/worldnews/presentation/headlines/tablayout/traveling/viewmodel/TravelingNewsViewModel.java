package ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.astondevs.worldnews.domain.remote.usecase.GetArticleUseCase;
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsCategoryUseCase;
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsUseCase;
import ru.astondevs.worldnews.domain.remote.usecase.GetRxJavaUseCase;
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem;

public class TravelingNewsViewModel extends ViewModel {

    private final GetRxJavaUseCase getRxJavaUseCase;
    private final GetNewsCategoryUseCase getNewsCategoryUseCase;

    @Inject
    public TravelingNewsViewModel(
            GetRxJavaUseCase getRxJavaUseCase,
            GetNewsCategoryUseCase getNewsCategoryUseCase
    ) {
        this.getRxJavaUseCase = getRxJavaUseCase;
        this.getNewsCategoryUseCase = getNewsCategoryUseCase;
    }

    public Single<List<NewsItem.Article>> getRxJava(String query) {
        return getRxJavaUseCase.invoke(query);
    }

    public Single<List<NewsItem.Article>> getNewsCategory(String category) {
        return getNewsCategoryUseCase.invoke(category);
    }

    public static ViewModelProvider.Factory factory(
            GetRxJavaUseCase getRxJavaUseCase,
            GetNewsCategoryUseCase getNewsCategoryUseCase
            ) {
        return new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass, CreationExtras extras) {
                return (T) new TravelingNewsViewModel(
                        getRxJavaUseCase,
                        getNewsCategoryUseCase
                );
            }
        };
    }

}
