package ru.astondevs.worldnews.presentation.headlines.tablayout.general.presenter;

import moxy.MvpView;

public interface GeneralView extends MvpView {
    void showData(String data);
}
