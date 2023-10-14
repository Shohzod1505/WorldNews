package ru.astondevs.worldnews.presentation.headlines.tablayout.business.screen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.astondevs.worldnews.databinding.FragmentBusinessNewsBinding;
import ru.astondevs.worldnews.presentation.common.detail.DetailFragmentNavigationListener;
import ru.astondevs.worldnews.presentation.headlines.tablayout.business.presenter.BusinessPresenter;
import ru.astondevs.worldnews.presentation.headlines.tablayout.business.presenter.BusinessView;

public class BusinessNewsFragmentMVP extends MvpAppCompatFragment implements BusinessView {

    @InjectPresenter
    BusinessPresenter businessPresenter;

    private FragmentBusinessNewsBinding binding;
    private DetailFragmentNavigationListener detailFragmentNavigationListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentNavigationListener) {
            detailFragmentNavigationListener = (DetailFragmentNavigationListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBusinessNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initAdapter();
    }

    @Override
    public void showData(String data) {

    }

//    private void initAdapter() {
//        NewsAdapter newsAdapter = new NewsAdapter(new NewsActionListener() {
//            @Override
//            public void onNewsAction(@NonNull NewsItem.News news) {
//                detailFragmentNavigationListener.goToDetailFragment(0);
//            }
//
//            @Override
//            public void onSourcesAction(@NonNull NewsItem.Sources sources) {}
//        });
//        newsAdapter.newsSubmitList(NewsRepository.INSTANCE.getNewsList());
//        initRecycler(requireContext(), binding.rvNews, newsAdapter);
//    }

}
