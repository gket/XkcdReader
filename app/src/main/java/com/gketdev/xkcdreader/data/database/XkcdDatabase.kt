package com.gketdev.xkcdreader.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gketdev.xkcdreader.data.database.dao.FavoriteDao
import com.gketdev.xkcdreader.data.database.entity.XkcdEntity

@Database(entities = [XkcdEntity::class], version = 1, exportSchema = false)
abstract class XkcdDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}