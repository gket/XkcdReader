package com.gketdev.xkcdreader.di

import android.content.Context
import androidx.room.Room
import com.gketdev.xkcdreader.R
import com.gketdev.xkcdreader.data.database.XkcdDatabase
import com.gketdev.xkcdreader.data.database.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): XkcdDatabase {
        return Room.databaseBuilder(
            appContext,
            XkcdDatabase::class.java,
            appContext.getString(R.string.app_name)
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: XkcdDatabase): FavoriteDao {
        return database.getFavoriteDao()
    }
}