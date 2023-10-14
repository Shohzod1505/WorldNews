package ru.astondevs.worldnews.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.astondevs.worldnews.data.datasource.local.AppDatabase
import ru.astondevs.worldnews.data.datasource.local.dao.NewsDao
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun getRoomDbInstance(
        context: Context
    ): AppDatabase {
        return AppDatabase.getAppDatabaseInstance(context)
    }

    @Singleton
    @Provides
    fun getNewsDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.getNewsDao()
    }

}
