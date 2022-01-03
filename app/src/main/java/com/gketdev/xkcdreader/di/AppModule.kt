package com.gketdev.xkcdreader.di

import com.gketdev.xkcdreader.data.repository.HomeRepository
import com.gketdev.xkcdreader.data.repository.HomeRepositoryImpl
import com.gketdev.xkcdreader.domain.GetItemUseCase
import com.gketdev.xkcdreader.domain.GetItemUseCaseImpl
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
    fun provideHomeRepository(repository: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    fun provideGetItemUseCase(useCase: GetItemUseCaseImpl): GetItemUseCase

}