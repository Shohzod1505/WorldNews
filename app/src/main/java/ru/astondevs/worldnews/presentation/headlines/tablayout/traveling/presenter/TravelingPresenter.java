package ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.presenter;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class TravelingPresenter extends MvpPresenter<TravelingView> {
    public void loadData() {
        getViewState().showData("Это загруженные данные");
    }
}
