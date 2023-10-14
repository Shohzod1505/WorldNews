package ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.screen;

import static ru.astondevs.worldnews.utils.UiKt.initRecycler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.astondevs.worldnews.databinding.FragmentTravelingNewsBinding;
import ru.astondevs.worldnews.presentation.common.adapter.NewsActionListener;
import ru.astondevs.worldnews.presentation.common.adapter.NewsAdapter;
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem;
import ru.astondevs.worldnews.presentation.common.detail.DetailFragmentNavigationListener;
import ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.viewmodel.TravelingNewsViewModel;

public class TravelingNewsFragment extends DaggerFragment {

    private FragmentTravelingNewsBinding binding;
    private DetailFragmentNavigationListener detailFragmentNavigationListener;
    private NewsAdapter newsAdapter;

    @Inject
    ViewModelProvider.Factory factory;

    private TravelingNewsViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentNavigationListener) {
            detailFragmentNavigationListener = (DetailFragmentNavigationListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, factory).get(TravelingNewsViewModel.class);
        binding = FragmentTravelingNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swiperRefresh();
        initAdapter();
    }

    private void initAdapter() {
        newsAdapter = new NewsAdapter(
                Glide.with(this),
                new NewsActionListener() {
                    @Override
                    public void onArticleAction(@NonNull NewsItem.Article article) {
                        detailFragmentNavigationListener.goToDetailFragment(article.getTitle());
                    }

                    @Override
                    public void onSourcesAction(@NonNull NewsItem.Source sources) {}
                });

        viewModel.getNewsCategory("sports")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<NewsItem.Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<NewsItem.Article> articles) {
                        newsAdapter.articleSubmitList(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

        initRecycler(requireContext(), binding.list.recyclerView, newsAdapter);
    }

    private void swiperRefresh() {
        SwipeRefreshLayout swipeRefreshLayout = binding.list.swipeView;

        swipeRefreshLayout.setOnRefreshListener(() -> {
            newsAdapter.articleSubmitList(new ArrayList<>()); // Очистка списка
            new Handler(Looper.getMainLooper()).post(() -> {
                swipeRefreshLayout.setRefreshing(true);
            });

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                viewModel.getNewsCategory("sports")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<NewsItem.Article>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }
                            @Override
                            public void onSuccess(List<NewsItem.Article> articles) {
                                newsAdapter.articleSubmitList(articles);
                            }
                            @Override
                            public void onError(Throwable e) {
                            }
                        });
                new Handler(Looper.getMainLooper()).post(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                });

            }, 0);
        });
    }

}
