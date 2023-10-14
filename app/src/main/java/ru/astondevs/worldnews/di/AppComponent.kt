package ru.astondevs.worldnews.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ru.astondevs.worldnews.App
import ru.astondevs.worldnews.di.module.FeatureModule
import ru.astondevs.worldnews.di.module.AppModule
import ru.astondevs.worldnews.di.module.NetworkModule
import ru.astondevs.worldnews.di.module.NewsModule
import ru.astondevs.worldnews.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Component(
    modules =[
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        FeatureModule::class,
        NetworkModule::class,
        NewsModule::class,
    ]
)
@Singleton
interface AppComponent {

    fun provideContext(): Context

    fun inject(application: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): AppComponent
    }

}
