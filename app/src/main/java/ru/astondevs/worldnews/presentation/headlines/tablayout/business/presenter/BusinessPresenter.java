package ru.astondevs.worldnews.presentation.headlines.tablayout.business.presenter;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class BusinessPresenter extends MvpPresenter<BusinessView> {
    public void loadData() {
        getViewState().showData("Это загруженные данные");
    }
}
