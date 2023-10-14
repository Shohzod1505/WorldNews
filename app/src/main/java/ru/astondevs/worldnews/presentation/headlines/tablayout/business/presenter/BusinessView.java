package ru.astondevs.worldnews.presentation.headlines.tablayout.business.presenter;

import moxy.MvpView;

public interface BusinessView extends MvpView {
    void showData(String data);
}
