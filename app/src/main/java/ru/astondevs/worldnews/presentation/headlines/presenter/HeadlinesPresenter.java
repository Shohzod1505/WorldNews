package ru.astondevs.worldnews.presentation.headlines.presenter;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class HeadlinesPresenter extends MvpPresenter<HeadlinesView> {
    public void loadData() {
        getViewState().showData("Это загруженные данные");
    }
}
