package ru.astondevs.worldnews.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.astondevs.worldnews.data.datasource.local.dao.NewsDao
import ru.astondevs.worldnews.data.datasource.local.entity.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object {
        private const val DATABASE_NAME = "news.db"
        private var db_instance: AppDatabase? = null

        fun getAppDatabaseInstance(context: Context): AppDatabase {

            if (db_instance == null) {
                db_instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return db_instance as AppDatabase
        }
    }

}
