package ru.astondevs.worldnews.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.astondevs.worldnews.di.FeatureScope
import ru.astondevs.worldnews.presentation.common.detail.screen.DetailFragment
import ru.astondevs.worldnews.presentation.common.detail.DetailModule
import ru.astondevs.worldnews.presentation.common.error.screen.ErrorFragment
import ru.astondevs.worldnews.presentation.common.error.ErrorModule
import ru.astondevs.worldnews.presentation.common.filter.screen.FilterFragment
import ru.astondevs.worldnews.presentation.common.filter.FilterModule
import ru.astondevs.worldnews.presentation.headlines.HeadlineModule
import ru.astondevs.worldnews.presentation.headlines.screen.HeadlinesFragment
import ru.astondevs.worldnews.presentation.headlines.tablayout.business.BusinessNewsModule
import ru.astondevs.worldnews.presentation.headlines.tablayout.business.screen.BusinessNewsFragment
import ru.astondevs.worldnews.presentation.headlines.tablayout.general.GeneralNewsModule
import ru.astondevs.worldnews.presentation.headlines.tablayout.general.screen.GeneralNewsFragment
import ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.TravelingNewsModule
import ru.astondevs.worldnews.presentation.headlines.tablayout.traveling.screen.TravelingNewsFragment
import ru.astondevs.worldnews.presentation.main.screen.MainActivity
import ru.astondevs.worldnews.presentation.main.MainModule
import ru.astondevs.worldnews.presentation.saved.screen.SavedFragment
import ru.astondevs.worldnews.presentation.saved.SavedModule
import ru.astondevs.worldnews.presentation.sources.screen.SourcesFragment
import ru.astondevs.worldnews.presentation.sources.SourcesModule
import ru.astondevs.worldnews.presentation.sources.news.screen.SourcesNewsFragment
import ru.astondevs.worldnews.presentation.sources.news.SourcesNewsModule

@Module
interface FeatureModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivity(): MainActivity

    @FeatureScope
    @ContributesAndroidInjector(modules = [SavedModule::class])
    fun contributeSavedFragment(): SavedFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [SourcesModule::class])
    fun contributeSourcesFragment(): SourcesFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [SourcesNewsModule::class])
    fun contributeSourcesNewsFragment(): SourcesNewsFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [FilterModule::class])
    fun contributeFilterFragment(): FilterFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [DetailModule::class])
    fun contributeDetailFragment(): DetailFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [ErrorModule::class])
    fun contributeErrorFragment(): ErrorFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [HeadlineModule::class])
    fun contributeHeadlinesFragment(): HeadlinesFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [GeneralNewsModule::class])
    fun contributeGeneralNewsFragment(): GeneralNewsFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [BusinessNewsModule::class])
    fun contributeBusinessNewsFragment(): BusinessNewsFragment

    @FeatureScope
    @ContributesAndroidInjector(modules = [TravelingNewsModule::class])
    fun contributeTravelingNewsFragment(): TravelingNewsFragment

}
