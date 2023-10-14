package ru.astondevs.worldnews.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.astondevs.worldnews.data.datasource.local.entity.Article

@Dao
interface NewsDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun save(article: Article)
//
//    @Delete
//    suspend fun delete(article: Article)

}
