package ru.astondevs.worldnews

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.astondevs.worldnews.di.AppComponent
import ru.astondevs.worldnews.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build().apply {
                inject(this@App)
            }

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    companion object {
        lateinit var appComponent: AppComponent
    }

}
