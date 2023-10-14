package ru.astondevs.worldnews.presentation.headlines.tablayout.general.presenter;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class GeneralPresenter extends MvpPresenter<GeneralView> {
    public void doSomething() {
        getViewState().showData("Hello, World!");
    }
}
