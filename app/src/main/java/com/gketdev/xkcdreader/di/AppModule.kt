package com.gketdev.xkcdreader.di

import com.gketdev.xkcdreader.domain.XkcdRepository
import com.gketdev.xkcdreader.data.repository.XkcdRepositoryImpl
import com.gketdev.xkcdreader.domain.GenericUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun provideHomeRepository(repository: XkcdRepositoryImpl): XkcdRepository
}