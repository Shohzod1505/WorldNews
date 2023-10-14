package ru.astondevs.worldnews.presentation.headlines.presenter;

import moxy.MvpView;

public interface HeadlinesView extends MvpView {
    void showData(String data);
}
